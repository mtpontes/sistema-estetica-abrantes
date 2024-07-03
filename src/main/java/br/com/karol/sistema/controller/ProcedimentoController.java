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

import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.service.ProcedimentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/procedimento")
public class ProcedimentoController {
    
    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosProcedimentoDTO > salvarProcedimento(@RequestBody @Valid CriarProcedimentoDTO procedimento) {
        return ResponseEntity.ok(service.salvar(procedimento));

    }

    @GetMapping
    public ResponseEntity<List<DadosProcedimentoDTO>> listarProcedimento() {
        return ResponseEntity.ok(service.listar());

    }

    @PutMapping("/{procedimentoId}")
    public ResponseEntity<DadosProcedimentoDTO > atualizarProcedimento(
        @PathVariable Long procedimentoId, 
        @RequestBody AtualizarProcedimentoDTO procedimento
        ) {
        return ResponseEntity.ok(service.atualizar(procedimentoId, procedimento));
    }

    @DeleteMapping("/{procedimentoId}")
    public ResponseEntity<Procedimento> removerProcedimento(@PathVariable Long procedimentoId) {
        service.remover(procedimentoId);
        return ResponseEntity.noContent().build();
    }
}
