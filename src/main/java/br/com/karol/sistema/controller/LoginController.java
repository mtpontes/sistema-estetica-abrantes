package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Login;
import br.com.karol.sistema.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/criarLogin")
    public ResponseEntity<Login> inserirLogin(@RequestBody Login login) {
        return ResponseEntity.ok(service.salvar(login).getBody());
    }

    @GetMapping("/buscarLogin")
    public ResponseEntity<Login> buscarLogin(@RequestBody String login) {
        return ResponseEntity.ok(service.buscarLogin(login));

    }

    @DeleteMapping("/deletarLogin")
    public ResponseEntity<Login> removerLogin(@RequestBody String login) {
        return ResponseEntity.ok(service.removerLogin(login).getBody());
    }

    @PutMapping("/atualizarLogin")
    public ResponseEntity<Login> atualizarLogin(@RequestBody Login login) {
        return ResponseEntity.ok(service.editarLogin(login).getBody());
    }

    @GetMapping("/listarLogin")
    public ResponseEntity<List<Login>> listarLogins() {
        return ResponseEntity.ok(service.listarLogin());
    }
}
