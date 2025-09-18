package com.clinicaveterinaria.repository;

import com.clinicaveterinaria.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operações com Agendamentos
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    /**
     * Verifica se existem agendamentos vinculados a um serviço
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.idServico = :servicoId")
    boolean existsByIdServico(@Param("servicoId") Long servicoId);
    
    /**
     * Conta quantos agendamentos estão vinculados a um serviço
     */
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.idServico = :servicoId")
    long countByIdServico(@Param("servicoId") Long servicoId);
    
    /**
     * Busca agendamentos por serviço
     */
    List<Agendamento> findByIdServico(Long servicoId);
    
    
    /**
     * Busca agendamentos por animal
     */
    List<Agendamento> findByIdAnimal(Long animalId);
    
    /**
     * Busca agendamentos por data
     */
    List<Agendamento> findByDataAgendamento(java.time.LocalDate data);
    
}

