package br.com.karol.sistema.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.karol.sistema.domain.Cliente;

public interface ClienteRepositoryCustom {
    Page<Cliente> findAllByParams(String nome, Pageable pageable);
}