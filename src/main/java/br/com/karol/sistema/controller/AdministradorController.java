package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @PostMapping("/salvarAdministrador")
    public ResponseEntity<Administrador> inserirAdministrador(@RequestBody Administrador administrador) {

        return ResponseEntity.ok(service.salvar(administrador).getBody());

    }

    @GetMapping("/listarAdministrador")
    public ResponseEntity<List<Administrador>> listarAdministrador() {
        return ResponseEntity.ok(service.listar().getBody());
    }

    @DeleteMapping("/deletarAdministrador")
    public ResponseEntity<Administrador> excluirAdministrador(@RequestBody Administrador administrador) {
        return service.excluir(administrador);
    }

    @PutMapping("/editarAdministrador")
    public ResponseEntity<Administrador> editarAdministrador(@RequestBody Administrador administrador) {
        return ResponseEntity.ok(service.editar(administrador).getBody());
    }

    @GetMapping("/buscarAdministrador")
    public ResponseEntity<Administrador> buscarAdministrador(@RequestParam Integer id) {
        return ResponseEntity.ok(service.buscarAdministradorPorId(id).getBody());

    }
}
