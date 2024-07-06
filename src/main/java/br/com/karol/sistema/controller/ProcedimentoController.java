package br.com.karol.sistema.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.service.ProcedimentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {
    
    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosProcedimentoDTO> criarProcedimento(@RequestBody @Valid CriarProcedimentoDTO procedimento) {
        return ResponseEntity.ok(service.salvarProcedimento(procedimento));
    }

    @GetMapping
    public ResponseEntity<List<DadosProcedimentoDTO>> listarProcedimentos() {
        return ResponseEntity.ok(service.listarTodosProcedimentos());
    }

    @GetMapping("/{procedimentoId}")
    public ResponseEntity<DadosProcedimentoDTO> buscarProcedimento(@PathVariable String procedimentoId) {
        return ResponseEntity.ok(service.mostrarProcedimento(procedimentoId));
    }

    @PutMapping("/{procedimentoId}")
    public ResponseEntity<DadosProcedimentoDTO> atualizarProcedimento(
        @PathVariable String procedimentoId, 
        @RequestBody AtualizarProcedimentoDTO procedimento
    ) {
        return ResponseEntity.ok(service.editarProcedimento(procedimentoId, procedimento));
    }

    @DeleteMapping("/{procedimentoId}")
    public ResponseEntity<Void> deletarProcedimento(@PathVariable String procedimentoId) {
        service.removerProcedimento(procedimentoId);
        return ResponseEntity.noContent().build();
    }
}