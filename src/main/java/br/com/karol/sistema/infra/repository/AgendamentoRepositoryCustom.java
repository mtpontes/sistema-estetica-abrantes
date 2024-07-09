package br.com.karol.sistema.infra.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;

public interface AgendamentoRepositoryCustom {
    Page<Agendamento> findAllByParams(
        StatusAgendamento status, 
        LocalDateTime minDataHora, 
        LocalDateTime maxDataHora, 
        String procedimentoId,
        String clienteId, 
        Pageable pageable
    );
}