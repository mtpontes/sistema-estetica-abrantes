package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.agendamento.AtualizarAgentamentoDTO;
import br.com.karol.sistema.dto.agendamento.CriaAgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.exceptions.EntityNotFoundException;
import br.com.karol.sistema.mapper.AgendamentoMapper;
import br.com.karol.sistema.repository.AgendamentoRepository;

@Service
@Transactional
public class AgendamentoService {

    private final String NOT_FOUND_DEFAULT_MESSAGE = "Agendamento não encontrado";

    private AgendamentoRepository repository;
    private ClienteService clienteService;
    
    private AgendamentoMapper mapper;

    public AgendamentoService(AgendamentoRepository repository, ClienteService clienteService, AgendamentoMapper mapper) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.mapper = mapper;
    }


    public DadosAgendamentoDTO buscarAgendamentoPorId(String id) {
        return mapper.toDadosAgentamentoDTO(this.getAgendamentoById(id));
    }

    public List<DadosAgendamentoDTO> listarTodosAgendamentos() {
        return mapper.toListDadosAgentamentoDTO(repository.findAll());
    }

    /* ! isso precisará ser readaptado !
     * 
     * É necessário fazer validações mais específicas sobre horários de agendamentos. É necessário levar em conta coisas como:
     * - Horário de abertura
     * - Horário de fechamento 
     * - Intervalo de tempo entre os agendamentos, considerando agentamentos anteriores e futuros dentro de um determinado tempo
     */
    public DadosAgendamentoDTO salvarAgendamento(CriaAgendamentoDTO dadosAgendamento, Usuario usuario) {
        Cliente clienteAlvo = clienteService.buscarPorId(dadosAgendamento.getIdCliente());
        
        repository.findByDataHora(dadosAgendamento.getDataHora())
            .orElseThrow(() -> new IllegalArgumentException("Horário indisponível"));

        Agendamento novoAgendamento = new Agendamento(
            new Procedimento(
                null, // id
                dadosAgendamento.getProcedimento().getNome(),
                dadosAgendamento.getProcedimento().getDescricao(),
                dadosAgendamento.getProcedimento().getValor()
            ),
            dadosAgendamento.getObservacao(),
            clienteAlvo,
            dadosAgendamento.getDataHora(),
            usuario
        );

        return mapper.toDadosAgentamentoDTO(repository.save(novoAgendamento));
    }

    /* Aqui deve ser implementado na entidade um método de update, precisa-se decidir sobre quais atributos de um agendamento
     * que podem ser modificados.
     * 
     * Se for possível alterar o horário do agendamento será necessário rodar as validações de horário assim como foi feito 
     * na criação de um agendamento
     */
    public DadosAgendamentoDTO  atualizarAgendamento(String id, AtualizarAgentamentoDTO dadosAtualizacao) {
        Agendamento agendamento = this.getAgendamentoById(id);
        agendamento.remarcarAgendamento(dadosAtualizacao.getObservacao(), dadosAtualizacao.getDataHora());
        
        repository.save(agendamento);
        return mapper.toDadosAgentamentoDTO(agendamento);
    }

    public void deletarAgendamento(String id) {
        this.repository.deleteById(id);
    }

    private Agendamento getAgendamentoById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_DEFAULT_MESSAGE));
    }
}