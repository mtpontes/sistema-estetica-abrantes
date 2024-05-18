package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Procedimento;
import org.springframework.data.repository.CrudRepository;

public interface ProcedimentoRepository extends CrudRepository<Procedimento, Long> {
}
