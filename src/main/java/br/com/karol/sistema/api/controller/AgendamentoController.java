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
import br.com.karol.sistema.api.dto.agendamento.ClienteCriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.CriarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
import br.com.karol.sistema.business.service.AgendamentoService;
import br.com.karol.sistema.business.service.DisponibilidadeService;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
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
        DadosAgendamentoDTO agendamentoCriado = 
            agendamentoService.salvarAgendamento(dadosAgendamento, usuario.getLogin());

        var uri = uriBuilder.path("/agendamento/{id}")
            .buildAndExpand(agendamentoCriado.getId())
            .toUri();

        return ResponseEntity.created(uri).body(agendamentoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<DadosBasicosAgendamentoDTO>> listarAgendamento(
        @RequestParam(required = false) Long idProcedimento,
        @RequestParam(required = false) String nomeProcedimento,
        @RequestParam(required = false) StatusAgendamento status,
        @RequestParam(required = false) LocalDateTime minDataHora, 
        @RequestParam(required = false) LocalDateTime maxDataHora,
        @RequestParam(required = false) String nomeCliente,
        @RequestParam(required = false) Long idCliente,
        @RequestParam(required = false) String cpfCliente,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(agendamentoService.listarTodosAgendamentos(
            idProcedimento,
            nomeProcedimento,
            status,
            minDataHora,
            maxDataHora,
            nomeCliente,
            idCliente,
            cpfCliente,
            pageable));
    }

    @GetMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> mostrarAgendamento(@PathVariable Long agendamentoId) {
        return ResponseEntity
            .ok()
            .body(agendamentoService.buscarAgendamentoPorId(agendamentoId));
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<LocalDateTime>> buscarHorariosDisponiveis(
        @RequestParam(required = true) Long procedimentoId, 
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate diaHora
    ) {
        if (diaHora.isBefore(LocalDate.now())) 
            throw new FieldValidationException("diaHora", "Deve ser uma data futura");
            
        return ResponseEntity.ok(disponibilidadeService
            .filtrarHorariosDisponiveis(procedimentoId, diaHora));
    }

    @PatchMapping("/{agendamentoId}")
    public ResponseEntity<DadosAgendamentoDTO> remarcarAgendamento(
        @PathVariable Long agendamentoId, 
        @RequestBody @Valid RemarcarAgendamentoDTO dadosRemarcacao
    ) {
        DadosAgendamentoDTO agendamentoAtualizado = 
            agendamentoService.editarDataHoraAgendamento(agendamentoId, dadosRemarcacao);
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }
    
    @PatchMapping("/{agendamentoId}/observacao")
    public ResponseEntity<ObservacaoAtualizadaAgendamentoDTO> atualizarObservacaoAgendamento(
        @PathVariable Long agendamentoId, 
        @RequestBody @Valid AtualizarObservacaoAgendamentoDTO dadosRemarcacao
    ) {
        ObservacaoAtualizadaAgendamentoDTO agendamentoAtualizado = 
            agendamentoService.editarObservacaoAgendamento(
                agendamentoId, 
                dadosRemarcacao
            );
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }

    @PatchMapping("/{agendamentoId}/status")
    public ResponseEntity<StatusAtualizadoAgendamentoDTO> atualizarStatusAgendamento(
        @PathVariable Long agendamentoId, 
        @RequestBody @Valid AtualizarStatusAgendamentoDTO novoStatus
    ) {
        return ResponseEntity.ok().body(
            agendamentoService.editarStatusAgendamento(agendamentoId, novoStatus));
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long agendamentoId) {
        agendamentoService.removerAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }

    // -- Rotas para clientes autenticados --

    @PostMapping("/me")
    public ResponseEntity<MeDadosAgendamentoDTO> criarAgendamentoMe(
        UriComponentsBuilder uriBuilder,
        Authentication authentication,
        @RequestBody @Valid ClienteCriarAgendamentoDTO dadosAgendamento
    ) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        MeDadosAgendamentoDTO agendamentoCriado = 
            agendamentoService.salvarAgendamentoMe(dadosAgendamento, usuario.getId());

        var uri = uriBuilder.path("/agendamento/{id}")
            .buildAndExpand(agendamentoCriado.getId())
            .toUri();

        return ResponseEntity.created(uri).body(agendamentoCriado);
    } 

    @GetMapping("/me")
    public ResponseEntity<Page<DadosBasicosAgendamentoDTO>> listarAgendamentoMe(
        Authentication auth,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        Usuario usuarioAtual = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(
            agendamentoService.listarTodosAgendamentosUsuarioAtual(
                usuarioAtual.getId(), 
                pageable)
            );
    }

    @GetMapping("/me/{agendamentoId}")
    public ResponseEntity<MeDadosAgendamentoDTO> mostrarAgendamentoMe(
        @PathVariable Long agendamentoId,
        Authentication auth
    ) {
        Usuario usuarioAtual = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok().body(
            agendamentoService.buscarAgendamentoPorIdEUsuarioId(
                agendamentoId, 
                usuarioAtual.getId())
            );
    }

    @PatchMapping("/me/{agendamentoId}")
    public ResponseEntity<MeDadosAgendamentoDTO> remarcarAgendamentoMe(
        @PathVariable Long agendamentoId, 
        Authentication auth,
        @RequestBody @Valid RemarcarAgendamentoDTO dadosRemarcacao
    ) {
        Usuario usuarioAtual = (Usuario) auth.getPrincipal();
        MeDadosAgendamentoDTO agendamentoAtualizado = agendamentoService
            .editarDataHoraAgendamentoUsuarioAtual(
                agendamentoId, 
                usuarioAtual.getId(), 
                dadosRemarcacao
            );
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }
    
    @PatchMapping("/me/{agendamentoId}/observacao")
    public ResponseEntity<ObservacaoAtualizadaAgendamentoDTO> atualizarObservacaoAgendamentoMe(
        @PathVariable Long agendamentoId, 
        Authentication auth,
        @RequestBody @Valid AtualizarObservacaoAgendamentoDTO dadosRemarcacao
    ) {
        Usuario usuarioAtual = (Usuario) auth.getPrincipal();
        ObservacaoAtualizadaAgendamentoDTO agendamentoAtualizado = 
            agendamentoService.editarObservacaoAgendamentoUsuarioAtual(
                agendamentoId, 
                usuarioAtual.getId(), 
                dadosRemarcacao
            );
        return ResponseEntity.ok().body(agendamentoAtualizado);
    }

    @PatchMapping("/me/{agendamentoId}/status")
    public ResponseEntity<StatusAtualizadoAgendamentoDTO> atualizarStatusAgendamentoMe(
        @PathVariable Long agendamentoId, 
        Authentication auth,
        @RequestBody @Valid AtualizarStatusAgendamentoDTO novoStatus
    ) {
        Usuario usuarioAtual = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok().body(
            agendamentoService.editarStatusAgendamentoUsuarioAtual(
                agendamentoId, 
                usuarioAtual, 
                novoStatus)
            );
    }
}