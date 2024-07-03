package br.com.karol.sistema.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.karol.sistema.domain.Relatorio;

public interface RelatorioRepository extends CrudRepository<Relatorio, Long> {
}