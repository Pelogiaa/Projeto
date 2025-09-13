package com.clinicaveterinaria.repository;

import com.clinicaveterinaria.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de banco de dados da entidade Animal
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
    /**
     * Busca animais por ID do cliente
     */
    List<Animal> findByIdCliente(Integer idCliente);
    
    /**
     * Busca animais por nome (case insensitive)
     */
    List<Animal> findByNomeAnimalContainingIgnoreCase(String nomeAnimal);
    
    /**
     * Busca animais por tipo
     */
    List<Animal> findByTipoAnimal(String tipoAnimal);
    
    /**
     * Busca animais por sexo
     */
    List<Animal> findBySexo(String sexo);
    
    /**
     * Busca animais com informações do cliente usando JOIN
     */
    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.cliente WHERE a.id = :id")
    Optional<Animal> findByIdWithCliente(@Param("id") Long id);
    
    /**
     * Busca todos os animais com informações do cliente
     */
    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.cliente ORDER BY a.nomeAnimal")
    List<Animal> findAllWithCliente();
    
    /**
     * Busca animais por cliente com informações do cliente
     */
    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.cliente WHERE a.idCliente = :idCliente ORDER BY a.nomeAnimal")
    List<Animal> findByIdClienteWithCliente(@Param("idCliente") Integer idCliente);
    
    /**
     * Busca animais por nome com informações do cliente
     */
    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.cliente WHERE LOWER(a.nomeAnimal) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY a.nomeAnimal")
    List<Animal> findByNomeWithCliente(@Param("nome") String nome);
    
    /**
     * Busca animais por tipo com informações do cliente
     */
    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.cliente WHERE a.tipoAnimal = :tipo ORDER BY a.nomeAnimal")
    List<Animal> findByTipoWithCliente(@Param("tipo") String tipo);
    
    /**
     * Conta total de animais
     */
    @Query("SELECT COUNT(a) FROM Animal a")
    Long countTotalAnimais();
    
    /**
     * Conta animais por tipo
     */
    @Query("SELECT COUNT(a) FROM Animal a WHERE a.tipoAnimal = :tipo")
    Long countByTipo(@Param("tipo") String tipo);
    
    /**
     * Conta animais por cliente
     */
    @Query("SELECT COUNT(a) FROM Animal a WHERE a.idCliente = :idCliente")
    Long countByIdCliente(@Param("idCliente") Integer idCliente);
    
    /**
     * Exclui todos os animais de um cliente
     */
    @Modifying
    @Query("DELETE FROM Animal a WHERE a.idCliente = :idCliente")
    void deleteByIdCliente(@Param("idCliente") Integer idCliente);
}
