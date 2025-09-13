package com.clinicaveterinaria.controller;

import com.clinicaveterinaria.dto.ServicoDTO;
import com.clinicaveterinaria.dto.ServicoDropdownDTO;
import com.clinicaveterinaria.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para operações com Serviços
 */
@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicoController {
    
    @Autowired
    private ServicoService servicoService;
    
    /**
     * Cria um novo serviço
     */
    @PostMapping
    public ResponseEntity<?> criarServico(@RequestBody ServicoDTO servicoDTO) {
        try {
            ServicoDTO servicoCriado = servicoService.criarServico(servicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Busca todos os serviços
     */
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> buscarTodosServicos() {
        try {
            List<ServicoDTO> servicos = servicoService.buscarTodosServicos();
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviços ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<ServicoDTO>> buscarServicosAtivos() {
        try {
            List<ServicoDTO> servicos = servicoService.buscarServicosAtivos();
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviços para dropdown
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<ServicoDropdownDTO>> buscarServicosParaDropdown() {
        try {
            List<ServicoDropdownDTO> servicos = servicoService.buscarServicosParaDropdown();
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviço por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarServicoPorId(@PathVariable Long id) {
        try {
            Optional<ServicoDTO> servico = servicoService.buscarServicoPorId(id);
            if (servico.isPresent()) {
                return ResponseEntity.ok(servico.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviços por termo de busca
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ServicoDTO>> buscarServicos(@RequestParam(required = false) String termo) {
        try {
            List<ServicoDTO> servicos = servicoService.buscarServicos(termo);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviços ativos por termo de busca
     */
    @GetMapping("/ativos/buscar")
    public ResponseEntity<List<ServicoDTO>> buscarServicosAtivos(@RequestParam(required = false) String termo) {
        try {
            List<ServicoDTO> servicos = servicoService.buscarServicosAtivos(termo);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca serviços por categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ServicoDTO>> buscarServicosPorCategoria(@PathVariable String categoria) {
        try {
            List<ServicoDTO> servicos = servicoService.buscarServicosPorCategoria(categoria);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Atualiza um serviço existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody ServicoDTO servicoDTO) {
        try {
            ServicoDTO servicoAtualizado = servicoService.atualizarServico(id, servicoDTO);
            return ResponseEntity.ok(servicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Deleta um serviço
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Altera status do serviço (ativa/desativa)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatusServico(@PathVariable Long id) {
        try {
            ServicoDTO servicoAtualizado = servicoService.alterarStatusServico(id);
            return ResponseEntity.ok(servicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Conta total de serviços
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarServicos() {
        try {
            long total = servicoService.contarServicos();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Conta serviços ativos
     */
    @GetMapping("/contar/ativos")
    public ResponseEntity<Long> contarServicosAtivos() {
        try {
            long total = servicoService.contarServicosAtivos();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Classe para resposta de erro
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
