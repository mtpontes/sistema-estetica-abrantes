package br.com.karol.sistema.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.agendamento.AtualizarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.validations.agendamento.AgendamentoValidator;
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
    private List<AgendamentoValidator> validator;


    @Transactional
    public DadosAgendamentoDTO salvarAgendamento(CriarAgendamentoDTO dadosAgendamento, Usuario usuario) {
        Agendamento forValidator = mapper.forAgendamentoValidator(dadosAgendamento.getClienteId(), dadosAgendamento.getDataHora(), null);
        validator.forEach(v -> v.validate(forValidator));
        
        Cliente clienteAlvo = clienteService.buscarPorId(dadosAgendamento.getClienteId());
        Procedimento procedimentoAlvo = procedimentoService.getProcedimentoById(dadosAgendamento.getProcedimentoId());

        Agendamento novoAgendamento = new Agendamento(
            procedimentoAlvo,
            dadosAgendamento.getStatus(),
            dadosAgendamento.getObservacao(),
            clienteAlvo,
            dadosAgendamento.getDataHora(),
            usuario);
        return mapper.toDadosAgendamentoDTO(agendamentoRepository.save(novoAgendamento));
    }

    public DadosAgendamentoDTO buscarAgendamentoPorId(String id) {
        return mapper.toDadosAgendamentoDTO(this.getAgendamentoById(id));
    }

    public List<DadosAgendamentoDTO> listarTodosAgendamentos() {
        return mapper.toListDadosAgentamentoDTO(agendamentoRepository.findAll());
    }

    @Transactional
    public DadosAgendamentoDTO editarAgendamento(String agendamentoId, AtualizarAgendamentoDTO dadosAtualizacao) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.setDataHora(dadosAtualizacao.getDataHora());
        validator.forEach(v -> v.validate(alvo));

        Agendamento agendamento = this.getAgendamentoById(agendamentoId);
        agendamento.remarcarAgendamento(dadosAtualizacao.getObservacao(), dadosAtualizacao.getDataHora());
        
        agendamentoRepository.save(agendamento);
        return mapper.toDadosAgendamentoDTO(agendamento);
    }

    @Transactional
    public DadosAgendamentoDTO editarStatusAgendamento(String agendamentoId, AtualizarStatusAgendamentoDTO dadosAtualizacao) {
        Agendamento alvo = this.getAgendamentoById(agendamentoId);
        alvo.atualizarStatus(dadosAtualizacao.getStatus());
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