package br.com.karol.sistema.api.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.karol.sistema.api.dto.agendamento.AtualizarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.business.service.AgendamentoService;
import br.com.karol.sistema.domain.Usuario;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;


    @PostMapping
    public ResponseEntity<DadosAgendamentoDTO> criarAgendamento(
        UriComponentsBuilder uriBuilder,
        Authentication authentication,
        @RequestBody @Valid CriarAgendamentoDTO dadosAgendamento
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

    @PatchMapping("/{agendamentoId}/status")
    public ResponseEntity<DadosAgendamentoDTO> atualizarStatusAgendamento(@PathVariable String agendamentoId, @RequestBody AtualizarStatusAgendamentoDTO dadosRemarcacao) {
        return ResponseEntity.ok().body(service.editarStatusAgendamento(agendamentoId, dadosRemarcacao));
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable String agendamentoId) {
        service.removerAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }
}