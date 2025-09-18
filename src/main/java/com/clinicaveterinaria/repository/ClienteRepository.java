package com.clinicaveterinaria.repository;

import com.clinicaveterinaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para operações com a entidade Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca cliente por email
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Busca cliente por CPF
     */
    Optional<Cliente> findByCpf(String cpf);
    
    /**
     * Verifica se existe cliente com o email informado
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe cliente com o CPF informado
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Busca clientes por nome (case insensitive)
     */
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca clientes por email (case insensitive)
     */
    List<Cliente> findByEmailContainingIgnoreCase(String email);
    
    /**
     * Busca clientes por telefone
     */
    List<Cliente> findByTelefoneContaining(String telefone);
    
    /**
     * Busca clientes por nome, email ou telefone (busca geral)
     */
    @Query("SELECT c FROM Cliente c WHERE " +
           "LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "c.telefone LIKE CONCAT('%', :termo, '%')")
    List<Cliente> buscarPorNomeEmailOuTelefone(@Param("termo") String termo);
    
    /**
     * Conta total de clientes
     */
    long count();
    
    /**
     * Busca clientes ordenados por data de cadastro (mais recentes primeiro)
     */
    List<Cliente> findAllByOrderByDataCadastroDesc();
}








































