package br.com.karol.sistema.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.karol.sistema.domain.Procedimento;

public interface ProcedimentoRepositoryCustom {
    
    Page<Procedimento> findAllByParams(
        String nome, 
        Double min, 
        Double max, 
        Pageable pageable);
}