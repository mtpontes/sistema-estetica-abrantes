package br.com.karol.sistema.business.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.agendamento.AtualizarObservacaoAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.validator.agendamento.AgendamentoValidator;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private static final String NOT_FOUND_MESSAGE = "Agendamento não encontrado";

    private AgendamentoRepository agendamentoRepository;
    private ClienteService clienteService;
    private ProcedimentoService procedimentoService;
    private AgendamentoMapper mapper;
    private List<AgendamentoValidator> validators;


    @Transactional
    public DadosAgendamentoDTO salvarAgendamento(CriarAgendamentoDTO dadosAgendamento, Usuario usuario) {
        Cliente clienteAlvo = clienteService.buscarPorId(dadosAgendamento.getClienteId());
        Procedimento procedimentoAlvo = procedimentoService.getProcedimentoById(dadosAgendamento.getProcedimentoId());

        Agendamento novoAgendamento = new Agendamento(
            procedimentoAlvo,
            dadosAgendamento.getStatus(),
            dadosAgendamento.getObservacao(),
            clienteAlvo,
            dadosAgendamento.getDataHora(),
            usuario,
            validators);
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(novoAgendamento));
    }

    public DadosAgendamentoDTO buscarAgendamentoPorId(String id) {
        return mapper.toDadosAgendamentoDTO(this.getAgendamentoById(id));
    }

    public Page<DadosAgendamentoDTO> listarTodosAgendamentos(
        StatusAgendamento status, 
        LocalDateTime minDataHora, 
        LocalDateTime maxDataHora, 
        String procedimentoId,
        String clienteId, 
        Pageable pageable
    ) {
        return mapper.toPageDadosAgentamentoDTO(agendamentoRepository.findAllByParams(status, minDataHora, maxDataHora, procedimentoId, clienteId, pageable));
    }

    @Transactional
    public DadosAgendamentoDTO editarDataHoraAgendamento(String agendamentoId, RemarcarAgendamentoDTO dadosNovaDataHora) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.remarcar(dadosNovaDataHora.getDataHora(), validators);
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(alvo));
    }

    @Transactional
    public DadosAgendamentoDTO editarObservacaoAgendamento(String agendamentoId, AtualizarObservacaoAgendamentoDTO dadosNovaObservacao) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.setObservacao(dadosNovaObservacao.getObservacao());
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(alvo));
    }

    @Transactional
    public DadosAgendamentoDTO editarStatusAgendamento(String agendamentoId, AtualizarStatusAgendamentoDTO novoStatus) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.atualizarStatus(novoStatus.getStatus());
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(alvo));
    }

    @Transactional
    public void removerAgendamento(String agendamentoId) {
        if (!agendamentoRepository.existsById(agendamentoId))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        agendamentoRepository.deleteById(agendamentoId);
    }

    private Agendamento getAgendamentoById(String id) {
        return agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}