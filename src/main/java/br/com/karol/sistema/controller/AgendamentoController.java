package br.com.karol.sistema.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.agendamento.AtualizarAgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.CriaAgendamentoDTO;
import br.com.karol.sistema.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.service.AgendamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosAgendamentoDTO> criarAgendamento(
        UriComponentsBuilder uriBuilder,
        Authentication authentication,
        @RequestBody @Valid CriaAgendamentoDTO dadosAgendamento
    ) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DadosAgendamentoDTO agendamentoCriado = service.salvarAgendamento(dadosAgendamento, usuario);

        var uri = uriBuilder.path("/agendamento/{id}")
            .buildAndExpand(agendamentoCriado.getId())
            .toUri();

        return ResponseEntity.created(uri).body(agendamentoCriado);
    }

    @GetMapping
    public ResponseEntity<List<DadosAgendamentoDTO>> listarAgendamento() {
        return ResponseEntity.ok(service.listarTodosAgendamentos());
    }

    @GetMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> buscarAgendamento(@PathVariable String agendamentoId) {
        return ResponseEntity.ok().body(service.buscarAgendamentoPorId(agendamentoId));
    }

    @PutMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> atualizarAgendamento(@PathVariable String agendamentoId, @RequestBody AtualizarAgendamentoDTO dadosRemarcacao) {
        DadosAgendamentoDTO agendamentoAtualizado = service.editarAgendamento(agendamentoId, dadosRemarcacao);
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable String agendamentoId) {
        service.removerAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }
}