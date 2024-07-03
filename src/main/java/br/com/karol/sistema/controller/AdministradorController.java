package br.com.karol.sistema.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.dto.administrador.CriarAdministradorDTO;
import br.com.karol.sistema.dto.administrador.DadosAdministradorDTO;
import br.com.karol.sistema.dto.administrador.AtualizarAdministradorDTO;
import br.com.karol.sistema.service.AdministradorService;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    private final AdministradorService service;
    
    public AdministradorController(AdministradorService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosAdministradorDTO> criarAdministrador(@RequestBody CriarAdministradorDTO dados) {
        return ResponseEntity.ok(service.salvar(dados));
    }

    @GetMapping
    public ResponseEntity<List<DadosAdministradorDTO>> listarAdministrador() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<DadosAdministradorDTO> buscarAdministrador(@PathVariable Long adminId) {
        return ResponseEntity.ok(service.buscarAdministradorPorId(adminId));
    }

    @PatchMapping("/{adminId}")
    public ResponseEntity<DadosAdministradorDTO> editarAdministrador(
        @PathVariable Long adminId, 
        @RequestBody AtualizarAdministradorDTO administrador
        ) {
        return ResponseEntity.ok(service.editarAdministrador(adminId, administrador));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> excluirAdministrador(@PathVariable Long adminId) {
        service.excluir(adminId);
        return ResponseEntity.noContent().build();
    }
}