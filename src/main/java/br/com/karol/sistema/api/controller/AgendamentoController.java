package br.com.karol.sistema.api.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.karol.sistema.api.dto.agendamento.AtualizarObservacaoAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.business.service.AgendamentoService;
import br.com.karol.sistema.business.service.DisponibilidadeService;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final DisponibilidadeService disponibilidadeService;


    @PostMapping
    public ResponseEntity<DadosAgendamentoDTO> criarAgendamento(
        UriComponentsBuilder uriBuilder,
        Authentication authentication,
        @RequestBody @Valid CriarAgendamentoDTO dadosAgendamento
    ) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        DadosAgendamentoDTO agendamentoCriado = agendamentoService.salvarAgendamento(dadosAgendamento, usuario);

        var uri = uriBuilder.path("/agendamento/{id}")
            .buildAndExpand(agendamentoCriado.getId())
            .toUri();

        return ResponseEntity.created(uri).body(agendamentoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<DadosAgendamentoDTO>> listarAgendamento(
        @RequestParam(required = false) StatusAgendamento status, 
        @RequestParam(required = false) LocalDateTime minDataHora, 
        @RequestParam(required = false) LocalDateTime maxDataHora, 
        @RequestParam(required = false) String procedimentoId,
        @RequestParam(required = false) String clienteId, 
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(agendamentoService.listarTodosAgendamentos(
            status, 
            minDataHora, 
            maxDataHora, 
            procedimentoId,
            clienteId, 
            pageable));
    }

    @GetMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> buscarAgendamento(@PathVariable String agendamentoId) {
        return ResponseEntity.ok().body(agendamentoService.buscarAgendamentoPorId(agendamentoId));
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<LocalDateTime>> buscarHorariosDisponiveis(
        @RequestParam(required = true) String procedimentoId, 
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia
    ) {
        return ResponseEntity.ok(disponibilidadeService.filtrarHorariosDisponiveis(procedimentoId, dia));
    }

    @PatchMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> remarcarAgendamento(@PathVariable String agendamentoId, @RequestBody @Valid RemarcarAgendamentoDTO dadosRemarcacao) {
        DadosAgendamentoDTO agendamentoAtualizado = agendamentoService.editarDataHoraAgendamento(agendamentoId, dadosRemarcacao);
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }
    
    @PatchMapping("/{agendamentoId}/observacao")
    public ResponseEntity<DadosAgendamentoDTO> atualizarObservacaoAgendamento(
        @PathVariable String agendamentoId, 
        @RequestBody @Valid AtualizarObservacaoAgendamentoDTO dadosRemarcacao
    ) {
        DadosAgendamentoDTO agendamentoAtualizado = agendamentoService.editarObservacaoAgendamento(agendamentoId, dadosRemarcacao);
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }

    @PatchMapping("/{agendamentoId}/status")
    public ResponseEntity<DadosAgendamentoDTO> atualizarStatusAgendamento(@PathVariable String agendamentoId, @RequestBody @Valid AtualizarStatusAgendamentoDTO novoStatus) {
        return ResponseEntity.ok().body(agendamentoService.editarStatusAgendamento(agendamentoId, novoStatus));
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable String agendamentoId) {
        agendamentoService.removerAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }
}