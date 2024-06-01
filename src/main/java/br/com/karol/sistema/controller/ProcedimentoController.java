package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.service.ProcedimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedimento")
public class ProcedimentoController {
    private final ProcedimentoService service;

    public ProcedimentoController(ProcedimentoService service) {
        this.service = service;
    }

    @PostMapping("/salvarProcedimento")
    public ResponseEntity<Procedimento> salvarProcedimento(@RequestBody Procedimento procedimento) {
        return ResponseEntity.ok(service.salvar(procedimento).getBody());

    }

    @GetMapping("/listarProcedimento")
    public ResponseEntity<List<Procedimento>> listarProcedimento() {
        return ResponseEntity.ok(service.listar());

    }

    @DeleteMapping("/deletarProcedimento")
    public ResponseEntity<Procedimento> removerProcedimento(@RequestBody Procedimento procedimento) {
        return ResponseEntity.ok(service.remover(procedimento).getBody());
    }

    @PutMapping("/atualizarProcedimento")
    public ResponseEntity<Procedimento> atualizarProcedimento(@RequestBody Procedimento procedimento) {
        return ResponseEntity.ok(service.atualizar(procedimento).getBody());
    }
}
