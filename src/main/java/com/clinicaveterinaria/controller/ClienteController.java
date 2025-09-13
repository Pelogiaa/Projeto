package com.clinicaveterinaria.controller;

import com.clinicaveterinaria.dto.ClienteDTO;
import com.clinicaveterinaria.dto.ClienteDropdownDTO;
import com.clinicaveterinaria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para operações com Cliente
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    /**
     * Cria um novo cliente
     * POST /api/clientes
     */
    @PostMapping
    public ResponseEntity<?> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteCriado = clienteService.criarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Busca todos os clientes
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodosClientes() {
        List<ClienteDTO> clientes = clienteService.buscarTodosClientes();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Busca clientes por termo de busca
     * GET /api/clientes?busca=termo
     */
    @GetMapping(params = "busca")
    public ResponseEntity<List<ClienteDTO>> buscarClientes(@RequestParam String busca) {
        List<ClienteDTO> clientes = clienteService.buscarClientes(busca);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Busca cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
        Optional<ClienteDTO> cliente = clienteService.buscarClientePorId(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Atualiza um cliente existente
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, 
                                            @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Deleta um cliente
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage()));
        }
    }
    
    /**
     * Conta total de clientes
     * GET /api/clientes/contar
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarClientes() {
        long total = clienteService.contarClientes();
        return ResponseEntity.ok(total);
    }
    
    /**
     * Verifica se cliente existe
     * GET /api/clientes/{id}/existe
     */
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> clienteExiste(@PathVariable Long id) {
        boolean existe = clienteService.clienteExiste(id);
        return ResponseEntity.ok(existe);
    }
    
    /**
     * Busca clientes para dropdown (apenas ID e nome)
     * GET /api/clientes/dropdown
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<ClienteDropdownDTO>> buscarClientesParaDropdown() {
        try {
            List<ClienteDropdownDTO> clientes = clienteService.buscarClientesParaDropdown();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Classe para respostas de erro
     */
    public static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
















