package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Procedimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedimentoRepository extends CrudRepository<Procedimento, Long> {
}
