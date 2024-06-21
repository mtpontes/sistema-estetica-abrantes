package br.com.karol.sistema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.agendamento.CriaAgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.repository.AgendamentoRepository;

//ctrl+o remove imports não utilizados
//@Transactional evita que determinados dados sejam persistidos no banco de dados caso haja algum tipo de problema
@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;
    @Autowired
    private ClienteService clienteService;


    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agendamento> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    /* ! isso precisará ser readaptado !
     * 
     * É necessário fazer validações mais específicas sobre horários de agendamentos. É necessário levar em conta coisas como:
     * - Horário de abertura
     * - Horário de fechamento 
     * - Intervalo de tempo entre os agendamentos, considerando agentamentos anteriores e futuros dentro de um determinado tempo
     */
    public DadosAgendamentoDTO salvar(CriaAgendamentoDTO dadosAgendamento, Usuario usuario) throws Exception {
        Cliente clienteAlvo = clienteService.buscarPorId(dadosAgendamento.getCliente().getId());
        
        repository.findByDataHora(dadosAgendamento.getDataHora())
            .orElseThrow(() -> new IllegalStateException("Horário indisponível"));

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

        novoAgendamento = repository.save(novoAgendamento);
        return new DadosAgendamentoDTO(novoAgendamento);
    }

    public Agendamento editar(Integer id, Agendamento agendamentoAtualizado) {
        if (repository.existsById(id)) {
            agendamentoAtualizado.setId(id);
            return repository.save(agendamentoAtualizado);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado");
        }
    }

}
