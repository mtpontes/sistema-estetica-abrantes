package br.com.karol.sistema.infra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.karol.sistema.domain.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
}