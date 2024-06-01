package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/salvarUsuario")
    public ResponseEntity<Usuario> inserirUsuario(@RequestBody Usuario usuario) {
        System.out.println("Usuario: " + usuario + " Salvo com sucesso!");
        return ResponseEntity.ok(service.salvar(usuario).getBody());

    }

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        System.out.println("Usuarios Cadastrados");
        return ResponseEntity.ok(service.listarTodos().getBody());
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<Usuario> buscarPorId(@RequestBody Integer id) {
        System.out.println("Usuario encontrado: " + id);
        return ResponseEntity.ok(service.buscarPorId(id).getBody());
    }

    @DeleteMapping("/removerPorId")
    public ResponseEntity removerPorId(@RequestParam Integer id) {
        service.removerPorId(id);
        return ResponseEntity.ok("Usuario Removido com sucesso!");
    }

    @PutMapping("/atualizarUsuario")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario, Integer id) {
        System.out.println("Usuario Atualizado: " + usuario);
        return ResponseEntity.ok(service.atualizar(id, usuario).getBody());
    }
}

