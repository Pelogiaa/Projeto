package com.clinicaveterinaria.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Serviço
 */
public class ServicoDTO {
    
    private Long id;
    private String nome;
    private String categoria;
    private String descricao;
    private BigDecimal preco;
    private String observacoes;
    private String status;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    
    // Construtores
    public ServicoDTO() {}
    
    public ServicoDTO(Long id, String nome, String categoria, String descricao, 
                     BigDecimal preco, String observacoes, String status) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.preco = preco;
        this.observacoes = observacoes;
        this.status = status;
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
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    @Override
    public String toString() {
        return "ServicoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", preco=" + preco +
                ", status='" + status + '\'' +
                '}';
    }
}
