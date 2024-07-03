package br.com.karol.sistema.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.karol.sistema.domain.Login;
import br.com.karol.sistema.service.LoginService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    
    @PostMapping
    public ResponseEntity<Login> salvarLogin(@RequestBody @Valid Login login) {
        return ResponseEntity.ok(service.salvar(login));
    }

    @GetMapping
    public ResponseEntity<List<Login>> listarLogins() {
        return ResponseEntity.ok(service.listarLogin());
    }

    @GetMapping("/{login}")
    public ResponseEntity<Login> buscarLogin(@PathVariable String login) {
        return ResponseEntity.ok(service.buscarLogin(login));
    }

    @PutMapping("/{login}")
    public ResponseEntity<Login> atualizarLogin(@PathVariable Login login) {
        return ResponseEntity.ok(service.editarLogin(login));
    }
    
    @DeleteMapping("/{login}")
    public ResponseEntity<Login> deletarLogin(@PathVariable String login) {
        service.removerLogin(login);
        return ResponseEntity.ok().build();
    }
}