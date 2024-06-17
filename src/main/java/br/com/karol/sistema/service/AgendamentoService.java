package br.com.karol.sistema.service;

import br.com.karol.sistema.config.ModelMapperConfig;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.dto.AgendamentoDTO;
import br.com.karol.sistema.exceptions.ClienteException;
import br.com.karol.sistema.repository.AgendamentoRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//ctrl+o remove imports não utilizados

@Service
//@Transactional evita que determinados dados sejam persistidos no banco de dados caso haja algum tipo de problema
@Transactional
@Data

public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final ClienteService clienteService;



    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }


    public Optional<Agendamento> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Agendamento salvar(Agendamento agendamento) throws Exception {
        //OBS: Optional.ofNullable(clienteOptional) cria um objeto Optional que contém o valor de clienteOptional ou é vazio se clienteOptional for null.
        // O método isPresent() verifica se o valor está presente e o método get() recupera o valor contido dentro do Optional.
        // Caso o valor não esteja presente, o bloco else lida com isso de forma apropriada.

        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteService.buscarPorId(agendamento.getCliente().getId()));
        if (clienteOptional.isEmpty()) {
            throw new ClienteException("Cliente não encontrado");
        }
        Optional byHorario = repository.findByDataHora(agendamento.getDataHora());
        if (byHorario.isPresent()) {
            throw new Exception("Horario indisponível");
        }
        // Associa o cliente encontrado ao agendamento
        agendamento.setCliente(clienteOptional.get());

        //Configura a data e hora atual
        agendamento.setDataCriacao(LocalDateTime.now());

        return repository.save(agendamento);
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
