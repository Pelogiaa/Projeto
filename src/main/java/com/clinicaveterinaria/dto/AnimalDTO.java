package com.clinicaveterinaria.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Animal
 */
public class AnimalDTO {
    
    private Long id;
    
    @NotNull(message = "ID do cliente é obrigatório")
    private Integer idCliente;
    
    @NotBlank(message = "Nome do animal é obrigatório")
    @Size(max = 100, message = "Nome do animal deve ter no máximo 100 caracteres")
    private String nomeAnimal;
    
    @NotNull(message = "Idade é obrigatória")
    @Min(value = 0, message = "Idade deve ser maior ou igual a 0")
    @Max(value = 50, message = "Idade deve ser menor ou igual a 50")
    private Integer idade;
    
    @NotBlank(message = "Tipo do animal é obrigatório")
    @Size(max = 50, message = "Tipo do animal deve ter no máximo 50 caracteres")
    private String tipoAnimal;
    
    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "^(Macho|Fêmea|FÃÂªmea)$", message = "Sexo deve ser 'Macho' ou 'Fêmea'")
    private String sexo;
    
    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que 0")
    @DecimalMax(value = "999.99", message = "Peso deve ser menor que 1000")
    private BigDecimal peso;
    
    @Size(max = 50, message = "Raça deve ter no máximo 50 caracteres")
    private String raca;
    
    @Size(max = 30, message = "Cor deve ter no máximo 30 caracteres")
    private String cor;
    
    private LocalDateTime dataCadastro;
    
    // Informações do cliente (para exibição)
    private String nomeCliente;
    private String emailCliente;
    
    // Construtores
    public AnimalDTO() {}
    
    public AnimalDTO(Long id, Integer idCliente, String nomeAnimal, Integer idade, String tipoAnimal, String sexo, BigDecimal peso) {
        this.id = id;
        this.idCliente = idCliente;
        this.nomeAnimal = nomeAnimal;
        this.idade = idade;
        this.tipoAnimal = tipoAnimal;
        this.sexo = sexo;
        this.peso = peso;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNomeAnimal() {
        return nomeAnimal;
    }
    
    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }
    
    public Integer getIdade() {
        return idade;
    }
    
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    
    public String getTipoAnimal() {
        return tipoAnimal;
    }
    
    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }
    
    public String getSexo() {
        return sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public BigDecimal getPeso() {
        return peso;
    }
    
    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
    
    public String getRaca() {
        return raca;
    }
    
    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    
    public String getNomeCliente() {
        return nomeCliente;
    }
    
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    
    public String getEmailCliente() {
        return emailCliente;
    }
    
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
    
    @Override
    public String toString() {
        return "AnimalDTO{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", nomeAnimal='" + nomeAnimal + '\'' +
                ", idade=" + idade +
                ", tipoAnimal='" + tipoAnimal + '\'' +
                ", sexo='" + sexo + '\'' +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                ", cor='" + cor + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                '}';
    }
}
