package br.com.karol.sistema.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.karol.sistema.domain.Agendamento;

public interface AgendamentoRepository extends MongoRepository<Agendamento, String> {
    Optional<Agendamento> findByDataHora(LocalDateTime dataHora);
}