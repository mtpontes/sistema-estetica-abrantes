package br.com.karol.sistema.infra.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Long countByClienteIdAndDataHoraBetween(Long clienteId, LocalDateTime inicio, LocalDateTime fim);
    
    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query(
        """
        SELECT COUNT(a) > 0 FROM Agendamento a 
        WHERE a.procedimento.id = :procedimentoId 
        AND a.status IN ('FINALIZADO', 'CANCELADO')
        """
    )
    boolean existsByProcedimentoIdAndStatusIn(Long procedimentoId);

    @Query(
        """
        SELECT a FROM Agendamento a WHERE
        (:procedimentoId IS NULL OR a.procedimento.id = :procedimentoId)
        AND (:procedimentoNome IS NULL OR LOWER(a.procedimento.nome) LIKE LOWER(CONCAT('%', :procedimentoNome, '%')))
        AND (:status IS NULL OR a.status = :status)
        AND (:minDataHora IS NULL OR a.dataHora >= :minDataHora)
        AND (:maxDataHora IS NULL OR a.dataHora <= :maxDataHora)
        AND (:clienteNome IS NULL OR LOWER(a.cliente.nome) LIKE LOWER(CONCAT('%', :clienteNome, '%')))
        AND (:clienteId IS NULL OR a.cliente.id = :clienteId)
        AND (:clienteCpf IS NULL OR a.cliente.cpf.value = :clienteCpf)
        """
    )
    Page<Agendamento> findAllByParams(
        Long procedimentoId, 
        String procedimentoNome, 
        StatusAgendamento status, 
        LocalDateTime minDataHora, 
        LocalDateTime maxDataHora, 
        String clienteNome,
        Long clienteId,
        String clienteCpf,
        Pageable pageable);

    Page<Agendamento> findByClienteUsuarioId(Long usuarioId, Pageable pageable);

    Optional<Agendamento> findByIdAndClienteUsuarioId(Long agendamentoId, Long usuarioId);
}