package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
    Optional findByDataHora(LocalDateTime dataHora);
}
