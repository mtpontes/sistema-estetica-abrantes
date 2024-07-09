package br.com.karol.sistema.infra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.karol.sistema.domain.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String>, ClienteRepositoryCustom {

    boolean existsByCpfValue(String cpf);

    boolean existsByTelefoneValue(String telefone);

    boolean existsByEmailValue(String email);
}