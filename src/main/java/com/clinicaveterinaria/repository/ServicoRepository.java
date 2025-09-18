package com.clinicaveterinaria.repository;

import com.clinicaveterinaria.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para operações com a entidade Servico
 */
@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
    /**
     * Busca serviços por nome (case insensitive)
     */
    List<Servico> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca serviços por status
     */
    List<Servico> findByStatus(Servico.StatusServico status);
    
    /**
     * Busca serviços ativos
     */
    List<Servico> findByStatusOrderByNome(Servico.StatusServico status);
    
    /**
     * Busca serviços por nome, descrição ou observações (busca geral)
     */
    @Query("SELECT s FROM Servico s WHERE " +
           "LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.observacoes) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Servico> buscarPorNomeDescricaoOuObservacoes(@Param("termo") String termo);
    
    /**
     * Busca serviços ativos por nome, descrição ou observações
     */
    @Query("SELECT s FROM Servico s WHERE s.status = 'ATIVO' AND (" +
           "LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(s.observacoes) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Servico> buscarAtivosPorNomeDescricaoOuObservacoes(@Param("termo") String termo);
    
    /**
     * Conta serviços por status
     */
    long countByStatus(Servico.StatusServico status);
    
    
    /**
     * Busca serviços por faixa de preço
     */
    @Query("SELECT s FROM Servico s WHERE s.preco BETWEEN :precoMin AND :precoMax")
    List<Servico> buscarPorFaixaPreco(@Param("precoMin") java.math.BigDecimal precoMin, 
                                     @Param("precoMax") java.math.BigDecimal precoMax);
    
    /**
     * Conta total de serviços
     */
    long count();
    
    /**
     * Busca serviços ordenados por nome
     */
    List<Servico> findAllByOrderByNome();
    
    /**
     * Busca serviços ordenados por preço (crescente)
     */
    List<Servico> findAllByOrderByPrecoAsc();
    
    /**
     * Busca serviços ordenados por preço (decrescente)
     */
    List<Servico> findAllByOrderByPrecoDesc();
    
    /**
     * Verifica se existe serviço com o nome informado
     */
    boolean existsByNome(String nome);
    
    /**
     * Verifica se existe serviço com o nome informado (excluindo um ID específico)
     */
    @Query("SELECT COUNT(s) > 0 FROM Servico s WHERE s.nome = :nome AND s.id != :id")
    boolean existsByNomeAndIdNot(@Param("nome") String nome, @Param("id") Long id);
}
