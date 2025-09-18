package com.clinicaveterinaria.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Entidade Agendamento
 */
@Entity
@Table(name = "tab_agendamentos")
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_agendamento", nullable = false)
    private LocalDateTime dataAgendamento;
    
    
    @Column(name = "id_animal", nullable = false)
    private Long idAnimal;
    
    @Column(name = "id_servico", nullable = false)
    private Long idServico;
    
    
    
    
    
    // Construtor padrão
    public Agendamento() {
    }
    
    // Construtor com parâmetros
    public Agendamento(LocalDateTime dataAgendamento, Long idAnimal, Long idServico) {
        this();
        this.dataAgendamento = dataAgendamento;
        this.idAnimal = idAnimal;
        this.idServico = idServico;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }
    
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    
    
    public Long getIdAnimal() {
        return idAnimal;
    }
    
    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }
    
    public Long getIdServico() {
        return idServico;
    }
    
    public void setIdServico(Long idServico) {
        this.idServico = idServico;
    }
    
    
    
    
    
}
