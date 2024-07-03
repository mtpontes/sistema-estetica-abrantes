package br.com.karol.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karol.sistema.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}