package br.com.karol.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karol.sistema.domain.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}