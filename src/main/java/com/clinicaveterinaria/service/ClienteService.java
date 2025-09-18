package com.clinicaveterinaria.service;

import com.clinicaveterinaria.dto.ClienteDTO;
import com.clinicaveterinaria.dto.ClienteDropdownDTO;
import com.clinicaveterinaria.model.Cliente;
import com.clinicaveterinaria.repository.ClienteRepository;
import com.clinicaveterinaria.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio com Cliente
 */
@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    /**
     * Converte Cliente para ClienteDTO
     */
    private ClienteDTO converterParaDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setCep(cliente.getCep());
        dto.setCpf(cliente.getCpf());
        dto.setDataCadastro(cliente.getDataCadastro());
        return dto;
    }
    
    /**
     * Converte ClienteDTO para Cliente
     */
    private Cliente converterParaEntidade(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCep(dto.getCep());
        cliente.setCpf(dto.getCpf());
        return cliente;
    }
    
    /**
     * Cria um novo cliente
     */
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        // Verificar se email já existe
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + clienteDTO.getEmail());
        }
        
        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + clienteDTO.getCpf());
        }
        
        Cliente cliente = converterParaEntidade(clienteDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return converterParaDTO(clienteSalvo);
    }
    
    /**
     * Busca todos os clientes
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarTodosClientes() {
        return clienteRepository.findAllByOrderByDataCadastroDesc()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca cliente por ID
     */
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::converterParaDTO);
    }
    
    /**
     * Busca clientes por termo de busca
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarClientes(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return buscarTodosClientes();
        }
        
        return clienteRepository.buscarPorNomeEmailOuTelefone(termo.trim())
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Atualiza um cliente existente
     */
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        // Verificar se email já existe em outro cliente
        if (!clienteExistente.getEmail().equals(clienteDTO.getEmail()) && 
            clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + clienteDTO.getEmail());
        }
        
        // Verificar se CPF já existe em outro cliente
        if (!clienteExistente.getCpf().equals(clienteDTO.getCpf()) && 
            clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + clienteDTO.getCpf());
        }
        
        // Atualizar dados
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());
        clienteExistente.setTelefone(clienteDTO.getTelefone());
        clienteExistente.setCep(clienteDTO.getCep());
        clienteExistente.setCpf(clienteDTO.getCpf());
        
        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return converterParaDTO(clienteAtualizado);
    }
    
    /**
     * Deleta um cliente e todos os animais associados
     */
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        
        // Excluir todos os animais associados ao cliente primeiro
        animalRepository.deleteByIdCliente(id.intValue());
        
        // Agora excluir o cliente
        clienteRepository.deleteById(id);
    }
    
    /**
     * Conta total de clientes
     */
    @Transactional(readOnly = true)
    public long contarClientes() {
        return clienteRepository.count();
    }
    
    /**
     * Verifica se cliente existe por ID
     */
    @Transactional(readOnly = true)
    public boolean clienteExiste(Long id) {
        return clienteRepository.existsById(id);
    }
    
    /**
     * Busca clientes para dropdown (apenas ID, nome e email)
     */
    @Transactional(readOnly = true)
    public List<ClienteDropdownDTO> buscarClientesParaDropdown() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteDropdownDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail()
                ))
                .collect(Collectors.toList());
    }
}
















