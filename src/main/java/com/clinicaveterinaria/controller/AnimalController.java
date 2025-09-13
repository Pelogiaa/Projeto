package com.clinicaveterinaria.controller;

import com.clinicaveterinaria.dto.AnimalDTO;
import com.clinicaveterinaria.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para operações de Animal
 */
@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "http://localhost:3000")
public class AnimalController {
    
    @Autowired
    private AnimalService animalService;
    
    /**
     * Lista todos os animais
     */
    @GetMapping
    public ResponseEntity<List<AnimalDTO>> listarTodos() {
        try {
            List<AnimalDTO> animais = animalService.listarTodos();
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca animal por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> buscarPorId(@PathVariable Long id) {
        try {
            Optional<AnimalDTO> animal = animalService.buscarPorId(id);
            return animal.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lista animais por cliente
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<AnimalDTO>> listarPorCliente(@PathVariable Integer idCliente) {
        try {
            List<AnimalDTO> animais = animalService.listarPorCliente(idCliente);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca animais por nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<AnimalDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<AnimalDTO> animais = animalService.buscarPorNome(nome);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Busca animais por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AnimalDTO>> buscarPorTipo(@PathVariable String tipo) {
        try {
            List<AnimalDTO> animais = animalService.buscarPorTipo(tipo);
            return ResponseEntity.ok(animais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Cria um novo animal
     */
    @PostMapping
    public ResponseEntity<AnimalDTO> criar(@Valid @RequestBody AnimalDTO animalDTO) {
        try {
            AnimalDTO animalCriado = animalService.salvar(animalDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(animalCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Atualiza um animal existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        try {
            AnimalDTO animalAtualizado = animalService.atualizar(id, animalDTO);
            return ResponseEntity.ok(animalAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Exclui um animal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            animalService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Conta total de animais
     */
    @GetMapping("/contar/total")
    public ResponseEntity<Long> contarTotal() {
        try {
            Long total = animalService.contarTotal();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Conta animais por tipo
     */
    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@PathVariable String tipo) {
        try {
            Long total = animalService.contarPorTipo(tipo);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Conta animais por cliente
     */
    @GetMapping("/contar/cliente/{idCliente}")
    public ResponseEntity<Long> contarPorCliente(@PathVariable Integer idCliente) {
        try {
            Long total = animalService.contarPorCliente(idCliente);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
