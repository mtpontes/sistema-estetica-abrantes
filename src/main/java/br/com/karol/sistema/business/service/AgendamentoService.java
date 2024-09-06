package br.com.karol.sistema.business.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.agendamento.AtualizarObservacaoAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ClienteCriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.constants.AgendamentoConstants;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.validator.AgendamentoValidator;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private static final String NOT_FOUND_MESSAGE = "Agendamento não encontrado";

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final ProcedimentoService procedimentoService;
    private final AgendamentoMapper mapper;
    private final List<AgendamentoValidator> validators;


    @Transactional
    public DadosAgendamentoDTO salvarAgendamento(
        CriarAgendamentoDTO dadosAgendamento, 
        String usuarioLogin
    ) {
        Cliente clienteAlvo = 
            clienteService.buscarPorId(dadosAgendamento.getClienteId());
        Procedimento procedimentoAlvo = procedimentoService
            .getProcedimentoById(dadosAgendamento.getProcedimentoId());

        Agendamento novoAgendamento = new Agendamento(
            procedimentoAlvo,
            dadosAgendamento.getStatus(),
            dadosAgendamento.getObservacao(),
            clienteAlvo,
            dadosAgendamento.getDataHora(),
            validators,
            usuarioLogin);
        return mapper.toDadosAgendamentoDTO(
            agendamentoRepository.save(novoAgendamento));
    }
    
    @Transactional
    public MeDadosAgendamentoDTO salvarAgendamentoMe(
        ClienteCriarAgendamentoDTO dadosAgendamento, 
        Long usuarioId
    ) {
        Cliente clienteAlvo = 
            clienteService.getClienteByUsuarioId(usuarioId);
        Procedimento procedimentoAlvo = procedimentoService
            .getProcedimentoById(dadosAgendamento.getProcedimentoId());

        Agendamento novoAgendamento = new Agendamento(
            procedimentoAlvo,
            dadosAgendamento.getStatus(),
            dadosAgendamento.getObservacao(),
            clienteAlvo,
            dadosAgendamento.getDataHora(),
            validators);
        return mapper.toMeDadosAgendamentoDTO(
            agendamentoRepository.save(novoAgendamento));
    }

    public DadosAgendamentoDTO buscarAgendamentoPorId(Long agendamentoId) {
        return mapper.toDadosAgendamentoDTO(this.getAgendamentoById(agendamentoId));
    }

    public MeDadosAgendamentoDTO buscarAgendamentoPorIdEUsuarioId(
        Long agendamentoId, Long usuarioId
    ) {
        Agendamento agendamento = agendamentoRepository
            .findByIdAndClienteUsuarioId(agendamentoId, usuarioId)
            .orElseThrow(EntityNotFoundException::new);
        return mapper.toMeDadosAgendamentoDTO(agendamento);
    }

    public Page<DadosBasicosAgendamentoDTO> listarTodosAgendamentos(
        Long idProcedimento,
        String nomeProcedimento,
        StatusAgendamento status, 
        LocalDateTime minDataHora, 
        LocalDateTime maxDataHora, 
        String nomeCliente,
        Long idCliente,
        String cpfCliente,
        Pageable pageable
    ) {
        return mapper.toPageDadosBasicosAgentamentoDTO(
            agendamentoRepository.findAllByParams(
                idProcedimento, 
                nomeProcedimento, 
                status, 
                minDataHora, 
                maxDataHora, 
                nomeCliente, 
                idCliente, 
                cpfCliente, 
                pageable
            )
        );
    }

    public Page<DadosBasicosAgendamentoDTO> listarTodosAgendamentosUsuarioAtual(Long usuarioId, Pageable pageable) {
        return mapper.toPageDadosBasicosAgentamentoDTO(
            agendamentoRepository.findByClienteUsuarioId(usuarioId, pageable));
    }

    @Transactional
    public DadosAgendamentoDTO editarDataHoraAgendamento(
        Long agendamentoId, 
        RemarcarAgendamentoDTO dadosNovaDataHora
    ) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.remarcar(dadosNovaDataHora.getDataHora(), validators);
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(alvo));
    }

    @Transactional
    public MeDadosAgendamentoDTO editarDataHoraAgendamentoUsuarioAtual(
        Long agendamentoId, 
        Long usuarioId,
        RemarcarAgendamentoDTO dadosNovaDataHora
    ) {
        Agendamento alvo = 
            this.getAgendamentoByIdAndUsuarioId(agendamentoId, usuarioId);
        alvo.remarcar(dadosNovaDataHora.getDataHora(), validators);
        return mapper.toMeDadosAgendamentoDTO(agendamentoRepository.save(alvo));
    }

    @Transactional
    public ObservacaoAtualizadaAgendamentoDTO editarObservacaoAgendamento(
        Long agendamentoId, 
        AtualizarObservacaoAgendamentoDTO dadosNovaObservacao
    ) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.setObservacao(dadosNovaObservacao.getObservacao());

        return mapper.toObservacaoAtualizadaAgendamentoDTO(
            agendamentoRepository.save(alvo)
        );
    }
    
    @Transactional
    public ObservacaoAtualizadaAgendamentoDTO editarObservacaoAgendamentoUsuarioAtual(
        Long agendamentoId, 
        Long usuarioId,
        AtualizarObservacaoAgendamentoDTO dadosNovaObservacao
    ) {
        Agendamento alvo = 
            this.getAgendamentoByIdAndUsuarioId(agendamentoId, usuarioId);
        alvo.setObservacao(dadosNovaObservacao.getObservacao());

        return mapper.toObservacaoAtualizadaAgendamentoDTO(
            agendamentoRepository.save(alvo)
        );
    }

    @Transactional
    public StatusAtualizadoAgendamentoDTO editarStatusAgendamento(
        Long agendamentoId, 
        AtualizarStatusAgendamentoDTO novoStatus
    ) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.atualizarStatus(novoStatus.getStatus());
        
        return mapper.toStatusAtualizadoAgendamentoDTO(
            agendamentoRepository.save(alvo)
        );
    }

    @Transactional
    public StatusAtualizadoAgendamentoDTO editarStatusAgendamentoUsuarioAtual(
        Long agendamentoId, 
        Usuario usuario, 
        AtualizarStatusAgendamentoDTO novoStatus
    ) {
        var permitidos =  AgendamentoConstants.statusesPermitidosParaClients;
        if (usuario.getRole() == UserRole.CLIENT 
            && !permitidos.contains(novoStatus.getStatus()))
                throw new AccessDeniedException("Sem permissão para alterar status para: " + novoStatus.getStatus());

        Agendamento alvo = 
            this.getAgendamentoByIdAndUsuarioId(agendamentoId, usuario.getId());
        alvo.atualizarStatus(novoStatus.getStatus());
        
        return mapper.toStatusAtualizadoAgendamentoDTO(
            agendamentoRepository.save(alvo)
        );
    }

    @Transactional
    public void removerAgendamento(Long agendamentoId) {
        if (!agendamentoRepository.existsById(agendamentoId))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        agendamentoRepository.deleteById(agendamentoId);
    }

    private Agendamento getAgendamentoById(Long agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    private Agendamento getAgendamentoByIdAndUsuarioId(Long agendamentoId, Long usuarioId) {
        return agendamentoRepository.findByIdAndClienteUsuarioId(
            agendamentoId, 
            usuarioId
        )
        .orElseThrow(EntityNotFoundException::new);
    }
}