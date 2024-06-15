package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Relatorio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends CrudRepository<Relatorio, Integer> {
}
