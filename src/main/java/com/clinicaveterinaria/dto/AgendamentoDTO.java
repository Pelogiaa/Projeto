package com.clinicaveterinaria.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * DTO para Agendamento
 */
public class AgendamentoDTO {
    
    private Long id;
    private LocalDateTime dataAgendamento;
    private Long idCliente;
    private Long idAnimal;
    private Long idServico;
    
    // Informações relacionadas (para exibição)
    private String nomeCliente;
    private String nomeAnimal;
    private String nomeServico;
    private Double precoServico;
    
    // Construtor padrão
    public AgendamentoDTO() {}
    
    // Construtor com parâmetros básicos
    public AgendamentoDTO(LocalDateTime dataAgendamento, Long idCliente, Long idAnimal, Long idServico) {
        this.dataAgendamento = dataAgendamento;
        this.idCliente = idCliente;
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
    
    public Long getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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
    
    
    
    
    
    public String getNomeCliente() {
        return nomeCliente;
    }
    
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    
    public String getNomeAnimal() {
        return nomeAnimal;
    }
    
    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }
    
    public String getNomeServico() {
        return nomeServico;
    }
    
    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
    
    public Double getPrecoServico() {
        return precoServico;
    }
    
    public void setPrecoServico(Double precoServico) {
        this.precoServico = precoServico;
    }
}
