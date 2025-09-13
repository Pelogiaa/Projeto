package com.clinicaveterinaria.dto;

import java.math.BigDecimal;

/**
 * DTO simplificado para dropdown de serviços (inclui preço)
 */
public class ServicoDropdownDTO {
    
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String categoria;
    
    // Construtores
    public ServicoDropdownDTO() {}
    
    public ServicoDropdownDTO(Long id, String nome, BigDecimal preco, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
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
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "ServicoDropdownDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
