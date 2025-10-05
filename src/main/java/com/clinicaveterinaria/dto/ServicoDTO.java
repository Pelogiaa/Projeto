package com.clinicaveterinaria.dto;

import java.math.BigDecimal;

/**
 * DTO para transferência de dados de Serviço
 */
public class ServicoDTO {
    
    private Long id;
    private String nome;
    private BigDecimal preco;
    
    // Construtores
    public ServicoDTO() {}
    
    public ServicoDTO(Long id, String nome, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    
    @Override
    public String toString() {
        return "ServicoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
