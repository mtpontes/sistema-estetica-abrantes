package br.com.karol.sistema.infra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.karol.sistema.domain.Procedimento;

public interface ProcedimentoRepository extends MongoRepository<Procedimento, String>, ProcedimentoRepositoryCustom {

    boolean existsByNome(String nome);
}