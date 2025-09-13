package com.clinicaveterinaria.service;

import com.clinicaveterinaria.dto.AnimalDTO;
import com.clinicaveterinaria.model.Animal;
import com.clinicaveterinaria.repository.AnimalRepository;
import com.clinicaveterinaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio da entidade Animal
 */
@Service
@Transactional
public class AnimalService {
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    /**
     * Salva um novo animal
     */
    public AnimalDTO salvar(AnimalDTO animalDTO) {
        // Verifica se o cliente existe
        clienteRepository.findById(animalDTO.getIdCliente().longValue())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + animalDTO.getIdCliente()));
        
        Animal animal = new Animal();
        animal.setIdCliente(animalDTO.getIdCliente());
        animal.setNomeAnimal(animalDTO.getNomeAnimal());
        animal.setIdade(animalDTO.getIdade());
        animal.setTipoAnimal(animalDTO.getTipoAnimal());
        animal.setSexo(animalDTO.getSexo());
        animal.setPeso(animalDTO.getPeso());
        animal.setRaca(animalDTO.getRaca());
        animal.setCor(animalDTO.getCor());
        animal.setDataCadastro(LocalDateTime.now());
        
        Animal animalSalvo = animalRepository.save(animal);
        return converterParaDTO(animalSalvo);
    }
    
    /**
     * Atualiza um animal existente
     */
    public AnimalDTO atualizar(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado com ID: " + id));
        
        // Verifica se o cliente existe
        clienteRepository.findById(animalDTO.getIdCliente().longValue())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + animalDTO.getIdCliente()));
        
        animal.setIdCliente(animalDTO.getIdCliente());
        animal.setNomeAnimal(animalDTO.getNomeAnimal());
        animal.setIdade(animalDTO.getIdade());
        animal.setTipoAnimal(animalDTO.getTipoAnimal());
        animal.setSexo(animalDTO.getSexo());
        animal.setPeso(animalDTO.getPeso());
        animal.setRaca(animalDTO.getRaca());
        animal.setCor(animalDTO.getCor());
        animal.setDataAtualizacao(LocalDateTime.now());
        
        Animal animalAtualizado = animalRepository.save(animal);
        return converterParaDTO(animalAtualizado);
    }
    
    /**
     * Busca animal por ID
     */
    @Transactional(readOnly = true)
    public Optional<AnimalDTO> buscarPorId(Long id) {
        return animalRepository.findByIdWithCliente(id)
                .map(this::converterParaDTO);
    }
    
    /**
     * Lista todos os animais
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> listarTodos() {
        return animalRepository.findAllWithCliente()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Lista animais por cliente
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> listarPorCliente(Integer idCliente) {
        return animalRepository.findByIdClienteWithCliente(idCliente)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca animais por nome
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> buscarPorNome(String nome) {
        return animalRepository.findByNomeWithCliente(nome)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca animais por tipo
     */
    @Transactional(readOnly = true)
    public List<AnimalDTO> buscarPorTipo(String tipo) {
        return animalRepository.findByTipoWithCliente(tipo)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Exclui um animal
     */
    public void excluir(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new RuntimeException("Animal não encontrado com ID: " + id);
        }
        animalRepository.deleteById(id);
    }
    
    /**
     * Conta total de animais
     */
    @Transactional(readOnly = true)
    public Long contarTotal() {
        return animalRepository.countTotalAnimais();
    }
    
    /**
     * Conta animais por tipo
     */
    @Transactional(readOnly = true)
    public Long contarPorTipo(String tipo) {
        return animalRepository.countByTipo(tipo);
    }
    
    /**
     * Conta animais por cliente
     */
    @Transactional(readOnly = true)
    public Long contarPorCliente(Integer idCliente) {
        return animalRepository.countByIdCliente(idCliente);
    }
    
    /**
     * Converte Animal para AnimalDTO
     */
    private AnimalDTO converterParaDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setIdCliente(animal.getIdCliente());
        dto.setNomeAnimal(animal.getNomeAnimal());
        dto.setIdade(animal.getIdade());
        dto.setTipoAnimal(animal.getTipoAnimal());
        dto.setSexo(animal.getSexo());
        dto.setPeso(animal.getPeso());
        dto.setRaca(animal.getRaca());
        dto.setCor(animal.getCor());
        dto.setDataCadastro(animal.getDataCadastro());
        dto.setDataAtualizacao(animal.getDataAtualizacao());
        
        // Adiciona informações do cliente se disponível
        if (animal.getCliente() != null) {
            dto.setNomeCliente(animal.getCliente().getNome());
            dto.setEmailCliente(animal.getCliente().getEmail());
        }
        
        return dto;
    }
}
