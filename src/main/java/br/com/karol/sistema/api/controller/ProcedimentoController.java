package br.com.karol.sistema.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.api.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.business.service.ProcedimentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/procedimentos")
@AllArgsConstructor
public class ProcedimentoController {
    
    private final ProcedimentoService service;


    @PostMapping
    public ResponseEntity<DadosProcedimentoDTO> criarProcedimento(
        @RequestBody @Valid CriarProcedimentoDTO procedimento
    ) {
        return ResponseEntity.ok(service.salvarProcedimento(procedimento));
    }

    @GetMapping
    public ResponseEntity<Page<DadosProcedimentoDTO>> listarProcedimentos(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) Double valorMin,
        @RequestParam(required = false) Double valorMax,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(service.listarTodosProcedimentos(nome, valorMin, valorMax, pageable));
    }

    @GetMapping("/{procedimentoId}")
    public ResponseEntity<DadosProcedimentoDTO> buscarProcedimento(@PathVariable Long procedimentoId) {
        return ResponseEntity.ok(service.mostrarProcedimento(procedimentoId));
    }

    @PutMapping("/{procedimentoId}")
    public ResponseEntity<DadosProcedimentoDTO> atualizarProcedimento(
        @PathVariable Long procedimentoId, 
        @RequestBody AtualizarProcedimentoDTO procedimento
    ) {
        return ResponseEntity.ok(service.editarProcedimento(procedimentoId, procedimento));
    }

    @DeleteMapping("/{procedimentoId}")
    public ResponseEntity<Void> deletarProcedimento(@PathVariable Long procedimentoId) {
        service.removerProcedimento(procedimentoId);
        return ResponseEntity.noContent().build();
    }
}