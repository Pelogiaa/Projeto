package com.clinicaveterinaria.controller;

import com.clinicaveterinaria.dto.AgendamentoDTO;
import com.clinicaveterinaria.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST para operações com Agendamentos
 */
@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "http://localhost:3000")
public class AgendamentoController {
    
    @Autowired
    private AgendamentoService agendamentoService;
    
    /**
     * Cria um novo agendamento
     */
    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            AgendamentoDTO agendamentoCriado = agendamentoService.criarAgendamento(agendamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Busca todos os agendamentos
     */
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> buscarTodosAgendamentos() {
        try {
            List<AgendamentoDTO> agendamentos = agendamentoService.buscarTodosAgendamentos();
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca agendamento por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAgendamentoPorId(@PathVariable Long id) {
        try {
            Optional<AgendamentoDTO> agendamento = agendamentoService.buscarAgendamentoPorId(id);
            if (agendamento.isPresent()) {
                return ResponseEntity.ok(agendamento.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Atualiza um agendamento existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            AgendamentoDTO agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, agendamentoDTO);
            return ResponseEntity.ok(agendamentoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Deleta um agendamento
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAgendamento(@PathVariable Long id) {
        try {
            agendamentoService.deletarAgendamento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    
    /**
     * Busca agendamentos por animal
     */
    @GetMapping("/animal/{idAnimal}")
    public ResponseEntity<List<AgendamentoDTO>> buscarAgendamentosPorAnimal(@PathVariable Long idAnimal) {
        try {
            List<AgendamentoDTO> agendamentos = agendamentoService.buscarAgendamentosPorAnimal(idAnimal);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca agendamentos por serviço
     */
    @GetMapping("/servico/{idServico}")
    public ResponseEntity<List<AgendamentoDTO>> buscarAgendamentosPorServico(@PathVariable Long idServico) {
        try {
            List<AgendamentoDTO> agendamentos = agendamentoService.buscarAgendamentosPorServico(idServico);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca agendamentos por data
     */
    @GetMapping("/data/{data}")
    public ResponseEntity<List<AgendamentoDTO>> buscarAgendamentosPorData(@PathVariable String data) {
        try {
            LocalDate dataAgendamento = LocalDate.parse(data);
            List<AgendamentoDTO> agendamentos = agendamentoService.buscarAgendamentosPorData(dataAgendamento);
            return ResponseEntity.ok(agendamentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
     * Conta total de agendamentos
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarAgendamentos() {
        try {
            long total = agendamentoService.contarAgendamentos();
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

