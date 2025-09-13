package com.clinicaveterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Animal representando a tabela tab_animais
 */
@Entity
@Table(name = "tab_animais")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "SERIAL")
    private Long id;
    
    @NotNull(message = "ID do cliente é obrigatório")
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;
    
    @NotBlank(message = "Nome do animal é obrigatório")
    @Size(max = 100, message = "Nome do animal deve ter no máximo 100 caracteres")
    @Column(name = "nome_animal", nullable = false, length = 100)
    private String nomeAnimal;
    
    @NotNull(message = "Idade é obrigatória")
    @Min(value = 0, message = "Idade deve ser maior ou igual a 0")
    @Max(value = 50, message = "Idade deve ser menor ou igual a 50")
    @Column(name = "idade", nullable = false)
    private Integer idade;
    
    @NotBlank(message = "Tipo do animal é obrigatório")
    @Size(max = 50, message = "Tipo do animal deve ter no máximo 50 caracteres")
    @Column(name = "tipo_animal", nullable = false, length = 50)
    private String tipoAnimal;
    
    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "^(Macho|Fêmea)$", message = "Sexo deve ser 'Macho' ou 'Fêmea'")
    @Column(name = "sexo", nullable = false, length = 10)
    private String sexo;
    
    @NotNull(message = "Peso é obrigatório")
    @DecimalMin(value = "0.1", message = "Peso deve ser maior que 0")
    @DecimalMax(value = "999.99", message = "Peso deve ser menor que 1000")
    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;
    
    @Size(max = 50, message = "Raça deve ter no máximo 50 caracteres")
    @Column(name = "raca", length = 50)
    private String raca;
    
    @Size(max = 30, message = "Cor deve ter no máximo 30 caracteres")
    @Column(name = "cor", length = 30)
    private String cor;
    
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    // Relacionamento com Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;
    
    // Construtores
    public Animal() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Animal(Integer idCliente, String nomeAnimal, Integer idade, String tipoAnimal, String sexo, BigDecimal peso) {
        this();
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
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    // Método para atualizar data de modificação
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Animal{" +
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
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
