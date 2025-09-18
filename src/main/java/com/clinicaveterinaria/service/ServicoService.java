package com.clinicaveterinaria.service;

import com.clinicaveterinaria.dto.ServicoDTO;
import com.clinicaveterinaria.dto.ServicoDropdownDTO;
import com.clinicaveterinaria.model.Servico;
import com.clinicaveterinaria.repository.ServicoRepository;
import com.clinicaveterinaria.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio com Servico
 */
@Service
@Transactional
public class ServicoService {
    
    @Autowired
    private ServicoRepository servicoRepository;
    
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    /**
     * Converte Servico para ServicoDTO
     */
    private ServicoDTO converterParaDTO(Servico servico) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setPreco(servico.getPreco());
        dto.setDescricao(servico.getDescricao());
        dto.setObservacoes(servico.getObservacoes());
        dto.setStatus(servico.getStatus().getDescricao());
        dto.setDataCadastro(servico.getDataCadastro());
        dto.setDataAtualizacao(servico.getDataAtualizacao());
        return dto;
    }
    
    /**
     * Converte ServicoDTO para Servico
     */
    private Servico converterParaEntidade(ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setId(dto.getId());
        servico.setNome(dto.getNome());
        servico.setPreco(dto.getPreco());
        servico.setDescricao(dto.getDescricao());
        servico.setObservacoes(dto.getObservacoes());
        
        // Converter string para enum
        if (dto.getStatus() != null) {
            try {
                servico.setStatus(Servico.StatusServico.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                servico.setStatus(Servico.StatusServico.ATIVO);
            }
        } else {
            servico.setStatus(Servico.StatusServico.ATIVO);
        }
        
        return servico;
    }
    
    /**
     * Cria um novo serviço
     */
    public ServicoDTO criarServico(ServicoDTO servicoDTO) {
        // Validações obrigatórias
        if (servicoDTO.getNome() == null || servicoDTO.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do serviço é obrigatório");
        }
        
        if (servicoDTO.getPreco() == null || servicoDTO.getPreco().doubleValue() <= 0) {
            throw new RuntimeException("Preço do serviço deve ser maior que zero");
        }
        
        // Verificar se nome já existe
        if (servicoRepository.existsByNome(servicoDTO.getNome())) {
            throw new RuntimeException("Serviço já cadastrado: " + servicoDTO.getNome());
        }
        
        Servico servico = converterParaEntidade(servicoDTO);
        Servico servicoSalvo = servicoRepository.save(servico);
        return converterParaDTO(servicoSalvo);
    }
    
    /**
     * Busca todos os serviços
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> buscarTodosServicos() {
        return servicoRepository.findAllByOrderByNome()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca serviços ativos
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> buscarServicosAtivos() {
        return servicoRepository.findByStatusOrderByNome(Servico.StatusServico.ATIVO)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    
    /**
     * Busca serviço por ID
     */
    @Transactional(readOnly = true)
    public Optional<ServicoDTO> buscarServicoPorId(Long id) {
        return servicoRepository.findById(id)
                .map(this::converterParaDTO);
    }
    
    /**
     * Busca serviços por termo de busca
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> buscarServicos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return buscarTodosServicos();
        }
        
        return servicoRepository.buscarPorNomeDescricaoOuObservacoes(termo.trim())
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    
    /**
     * Atualiza um serviço existente
     */
    public ServicoDTO atualizarServico(Long id, ServicoDTO servicoDTO) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
        
        // Verificar se nome já existe em outro serviço
        if (!servicoExistente.getNome().equals(servicoDTO.getNome()) && 
            servicoRepository.existsByNomeAndIdNot(servicoDTO.getNome(), id)) {
            throw new RuntimeException("Serviço já cadastrado: " + servicoDTO.getNome());
        }
        
        // Atualizar dados
        servicoExistente.setNome(servicoDTO.getNome());
        servicoExistente.setPreco(servicoDTO.getPreco());
        servicoExistente.setDescricao(servicoDTO.getDescricao());
        servicoExistente.setObservacoes(servicoDTO.getObservacoes());
        servicoExistente.setDataAtualizacao(LocalDateTime.now());
        
        // Converter string para enum
        if (servicoDTO.getStatus() != null) {
            try {
                servicoExistente.setStatus(Servico.StatusServico.valueOf(servicoDTO.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Manter status atual se inválido
            }
        }
        
        Servico servicoAtualizado = servicoRepository.save(servicoExistente);
        return converterParaDTO(servicoAtualizado);
    }
    
    /**
     * Deleta um serviço
     */
    public void deletarServico(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado com ID: " + id);
        }
        
        // Verificar se existem agendamentos vinculados ao serviço
        if (existeAgendamentosVinculados(id)) {
            long quantidadeAgendamentos = contarAgendamentosVinculados(id);
            throw new RuntimeException("Não é possível excluir o serviço. Existem " + quantidadeAgendamentos + 
                    " agendamento(s) vinculado(s) a este serviço. " +
                    "Primeiro, exclua ou altere os agendamentos relacionados, ou desative o serviço em vez de excluí-lo.");
        }
        
        servicoRepository.deleteById(id);
    }
    
    /**
     * Verifica se existem agendamentos vinculados ao serviço
     */
    @Transactional(readOnly = true)
    public boolean existeAgendamentosVinculados(Long servicoId) {
        return agendamentoRepository.existsByIdServico(servicoId);
    }
    
    /**
     * Conta quantos agendamentos estão vinculados ao serviço
     */
    @Transactional(readOnly = true)
    public long contarAgendamentosVinculados(Long servicoId) {
        return agendamentoRepository.countByIdServico(servicoId);
    }
    
    
    /**
     * Conta total de serviços
     */
    @Transactional(readOnly = true)
    public long contarServicos() {
        return servicoRepository.count();
    }
    
    
    /**
     * Verifica se serviço existe por ID
     */
    @Transactional(readOnly = true)
    public boolean servicoExiste(Long id) {
        return servicoRepository.existsById(id);
    }
    
    /**
     * Busca serviços ativos por termo de busca
     */
    @Transactional(readOnly = true)
    public List<ServicoDTO> buscarServicosAtivos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return buscarServicosAtivos();
        }
        
        return servicoRepository.buscarAtivosPorNomeDescricaoOuObservacoes(termo.trim())
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Altera status do serviço (ativa/desativa)
     */
    public ServicoDTO alterarStatusServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
        
        // Alternar status
        if (servico.getStatus() == Servico.StatusServico.ATIVO) {
            servico.setStatus(Servico.StatusServico.INATIVO);
        } else {
            servico.setStatus(Servico.StatusServico.ATIVO);
        }
        servico.setDataAtualizacao(LocalDateTime.now());
        
        Servico servicoAtualizado = servicoRepository.save(servico);
        return converterParaDTO(servicoAtualizado);
    }
    
    /**
     * Conta serviços ativos
     */
    @Transactional(readOnly = true)
    public long contarServicosAtivos() {
        return servicoRepository.countByStatus(Servico.StatusServico.ATIVO);
    }
    
    /**
     * Busca serviços para dropdown (apenas ID, nome e preço)
     */
    @Transactional(readOnly = true)
    public List<ServicoDropdownDTO> buscarServicosParaDropdown() {
        return servicoRepository.findAllByOrderByNome()
                .stream()
                .filter(servico -> servico.getStatus() == Servico.StatusServico.ATIVO || 
                                 (servico.getStatus() != null && servico.getStatus().getDescricao().equals("Ativo")))
                .map(servico -> new ServicoDropdownDTO(
                    servico.getId(),
                    servico.getNome(),
                    servico.getPreco()
                ))
                .collect(Collectors.toList());
    }
}
