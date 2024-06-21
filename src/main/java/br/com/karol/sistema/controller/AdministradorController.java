package br.com.karol.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.karol.sistema.domain.Administrador;
import br.com.karol.sistema.dto.administrador.DadosAdministradorDTO;
import br.com.karol.sistema.service.AdministradorService;

@RestController
@RequestMapping("/administrador")

public class AdministradorController {

    @Autowired
    private AdministradorService service;

    @PostMapping("/salvarAdministrador")
    public ResponseEntity<DadosAdministradorDTO> inserirAdministrador(@RequestBody Administrador administrador) {
        return ResponseEntity.ok(service.salvar(administrador));
    }

    @GetMapping("/listarAdministrador")
    public ResponseEntity<List<DadosAdministradorDTO>> listarAdministrador() {
        return ResponseEntity.ok(service.listar());
    }

    @DeleteMapping("/deletarAdministrador/{administradorID}")
    public ResponseEntity<Void> excluirAdministrador(@PathVariable Integer administradorID) {
        service.excluir(administradorID);
        return ResponseEntity.noContent().build();
    }

    // pode ser uma boa ideia trocar pelo método HTTP PATCH, pois está fazendo a atualização de um único dado desse recurso, e não substituindo o recurso em si
    @PutMapping("/editarAdministrador")
    public ResponseEntity<DadosAdministradorDTO> editarAdministrador(@RequestBody Administrador administrador) {
        return ResponseEntity.ok(service.editar(administrador));
    }

    @GetMapping("/buscarAdministrador")
    public ResponseEntity<DadosAdministradorDTO> buscarAdministrador(@RequestParam Integer id) {
        return ResponseEntity.ok(service.buscarAdministradorPorId(id));
    }
}
