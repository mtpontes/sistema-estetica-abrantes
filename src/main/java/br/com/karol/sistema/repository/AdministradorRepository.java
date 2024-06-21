package br.com.karol.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.karol.sistema.domain.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
}
