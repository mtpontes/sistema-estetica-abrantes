package br.com.karol.sistema.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.agendamento.AgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.CriaAgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.mapper.AgendamentoMapper;
import br.com.karol.sistema.service.AgendamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;
    @Autowired
    private AgendamentoMapper mapper;


    @GetMapping("/listarAgendamentos")
    public List<AgendamentoDTO> listarAgendamento() {
        List<Agendamento> agendamentos = service.listarTodos();
        return mapper.agendamentoListToAgendamentoDTOList(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Agendamento> optional = service.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AgendamentoDTO agendamentoDTO = mapper.agendamentoToAgendamentoDTO(optional.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoDTO);

    }

    @Transactional
    @PostMapping
    public ResponseEntity<DadosAgendamentoDTO> salvar(
        @Valid @RequestBody CriaAgendamentoDTO dadosAgendamento, 
        UriComponentsBuilder uriBuilder,
        Authentication authentication
        ) throws Exception {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DadosAgendamentoDTO dadosAgendamentoCriado = service.salvar(dadosAgendamento, usuario);

        var uri = uriBuilder.path("/agendamento/{id}")
            .buildAndExpand(dadosAgendamentoCriado.getId())
            .toUri();

        //AgendamentoDTO agendamentoDTO = mapper.agendamentoToAgendamentoDTO(agendar); - não tinha visto que tinha um mapper aqui, agora já criei os construtores...
        return ResponseEntity.created(uri).body(dadosAgendamentoCriado);
    }

//    @PutMapping
//    public ResponseEntity<AgendamentoDTO> atualizar(@PathVariable Integer id, @RequestBody Agendamento agendamento) {
//        Optional<Agendamento> optional = service.buscarPorId(agendamento.getId());
//        if (optional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//
//
//
//        }
//
//    }

//    @DeleteMapping
//    public ResponseEntity<AgendamentoDTO> deletar(@RequestBody Agendamento agendamento) {
//
//    }
}
