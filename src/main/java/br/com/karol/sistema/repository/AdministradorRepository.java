package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Administrador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends CrudRepository<Administrador, Integer> {
}
