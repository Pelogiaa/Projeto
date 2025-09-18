package com.clinicaveterinaria.service;

import com.clinicaveterinaria.dto.AgendamentoDTO;
import com.clinicaveterinaria.model.Agendamento;
import com.clinicaveterinaria.repository.AgendamentoRepository;
import com.clinicaveterinaria.repository.ClienteRepository;
import com.clinicaveterinaria.repository.AnimalRepository;
import com.clinicaveterinaria.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio com Agendamentos
 */
@Service
@Transactional
public class AgendamentoService {
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private ServicoRepository servicoRepository;
    
    /**
     * Converte Agendamento para AgendamentoDTO
     */
    private AgendamentoDTO converterParaDTO(Agendamento agendamento) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataAgendamento(agendamento.getDataAgendamento());
        dto.setIdAnimal(agendamento.getIdAnimal());
        dto.setIdServico(agendamento.getIdServico());
        
        // Buscar informações relacionadas
        // Cliente será obtido através do animal
        animalRepository.findById(agendamento.getIdAnimal()).ifPresent(animal -> {
            dto.setIdCliente(animal.getIdCliente().longValue());
            clienteRepository.findById(animal.getIdCliente().longValue()).ifPresent(cliente -> 
                dto.setNomeCliente(cliente.getNome())
            );
        });
        
        animalRepository.findById(agendamento.getIdAnimal()).ifPresent(animal -> 
            dto.setNomeAnimal(animal.getNomeAnimal())
        );
        
        servicoRepository.findById(agendamento.getIdServico()).ifPresent(servico -> {
            dto.setNomeServico(servico.getNome());
            dto.setPrecoServico(servico.getPreco().doubleValue());
        });
        
        return dto;
    }
    
    /**
     * Converte AgendamentoDTO para Agendamento
     */
    private Agendamento converterParaEntidade(AgendamentoDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setDataAgendamento(dto.getDataAgendamento());
        agendamento.setIdAnimal(dto.getIdAnimal());
        agendamento.setIdServico(dto.getIdServico());
        
        return agendamento;
    }
    
    /**
     * Cria um novo agendamento
     */
    public AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO) {
        // Validar se animal existe
        if (!animalRepository.existsById(agendamentoDTO.getIdAnimal())) {
            throw new RuntimeException("Animal não encontrado com ID: " + agendamentoDTO.getIdAnimal());
        }
        
        // Validar se serviço existe
        if (!servicoRepository.existsById(agendamentoDTO.getIdServico())) {
            throw new RuntimeException("Serviço não encontrado com ID: " + agendamentoDTO.getIdServico());
        }
        
        Agendamento agendamento = converterParaEntidade(agendamentoDTO);
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return converterParaDTO(agendamentoSalvo);
    }
    
    /**
     * Busca todos os agendamentos
     */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> buscarTodosAgendamentos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca agendamento por ID
     */
    @Transactional(readOnly = true)
    public Optional<AgendamentoDTO> buscarAgendamentoPorId(Long id) {
        return agendamentoRepository.findById(id)
                .map(this::converterParaDTO);
    }
    
    /**
     * Atualiza um agendamento existente
     */
    public AgendamentoDTO atualizarAgendamento(Long id, AgendamentoDTO agendamentoDTO) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
        
        
        // Validar se animal existe
        if (!animalRepository.existsById(agendamentoDTO.getIdAnimal())) {
            throw new RuntimeException("Animal não encontrado com ID: " + agendamentoDTO.getIdAnimal());
        }
        
        // Validar se serviço existe
        if (!servicoRepository.existsById(agendamentoDTO.getIdServico())) {
            throw new RuntimeException("Serviço não encontrado com ID: " + agendamentoDTO.getIdServico());
        }
        
        // Atualizar dados
        agendamentoExistente.setDataAgendamento(agendamentoDTO.getDataAgendamento());
        agendamentoExistente.setIdAnimal(agendamentoDTO.getIdAnimal());
        agendamentoExistente.setIdServico(agendamentoDTO.getIdServico());
        
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        return converterParaDTO(agendamentoAtualizado);
    }
    
    /**
     * Deleta um agendamento
     */
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado com ID: " + id);
        }
        
        agendamentoRepository.deleteById(id);
    }
    
    
    /**
     * Busca agendamentos por animal
     */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> buscarAgendamentosPorAnimal(Long idAnimal) {
        return agendamentoRepository.findByIdAnimal(idAnimal).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca agendamentos por serviço
     */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> buscarAgendamentosPorServico(Long idServico) {
        return agendamentoRepository.findByIdServico(idServico).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca agendamentos por data
     */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> buscarAgendamentosPorData(java.time.LocalDate data) {
        return agendamentoRepository.findByDataAgendamento(data).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    
    /**
     * Conta total de agendamentos
     */
    @Transactional(readOnly = true)
    public long contarAgendamentos() {
        return agendamentoRepository.count();
    }
    
    /**
     * Verifica se agendamento existe por ID
     */
    @Transactional(readOnly = true)
    public boolean agendamentoExiste(Long id) {
        return agendamentoRepository.existsById(id);
    }
}

