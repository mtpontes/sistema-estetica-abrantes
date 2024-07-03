package br.com.karol.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karol.sistema.domain.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}