package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Agendamento;
import org.springframework.data.repository.CrudRepository;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer> {
}
