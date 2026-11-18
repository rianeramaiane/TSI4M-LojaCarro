package br.org.edu.ifrn.LojaCarro.repository;

import br.org.edu.ifrn.LojaCarro.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Essa query inteligente do Spring avalia se já existe um test drive marcado para 
    // aquele carro específico dentro da margem de tempo fornecida.
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.carro.id = :carroId AND " +
           "a.horario < :fim AND a.horario > :inicio")
    boolean existeConflitoDeHorario(
            @Param("carroId") Long carroId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}