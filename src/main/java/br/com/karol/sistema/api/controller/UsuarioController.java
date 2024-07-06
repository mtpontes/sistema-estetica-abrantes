package br.com.karol.sistema.api.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.domain.Usuario;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosUsuarioDTO> criarUsuario(@RequestBody @Valid CriarUsuarioDTO dados) {
        return ResponseEntity.ok(service.salvarUsuario(dados));
    }

    /* O usuário padrão precisa se autenticar e só consegue consultar os seus próprios dados */
    @GetMapping
    public ResponseEntity<DadosUsuarioDTO> buscarPorAutenticacao(Authentication authentication) {
        return ResponseEntity.ok(service.getDadosUsuarioAtual((Usuario) authentication.getPrincipal()));
    }

    /* A atualização do usuário é baseada na recuperação da identificação desse usuário através do token de autenticação */
    @PutMapping
    public ResponseEntity<DadosUsuarioDTO> atualizarUsuario(Authentication authentication, @RequestBody AtualizarUsuarioDTO dados) {
        return ResponseEntity.ok(service.atualizarUsuarioAtual((Usuario) authentication.getPrincipal(), dados));
    }
    
    // --- Rotas de admins -- 

    @PostMapping("/admin")
    public ResponseEntity<DadosUsuarioDTO> adminCriarUsuario(@RequestBody @Valid CriarUsuarioDTO dados) {
        return ResponseEntity.ok(service.adminSalvarUsuario(dados));
    }
    
    @GetMapping("/admin")
    public ResponseEntity<List<DadosUsuarioDTO>> listarUsuarios(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(service.listarTodosUsuarios(pageable));
    }

    /* Um usuário com ROLE ADMIN autenticado consegue recuperar dados de outros usuários */
    @GetMapping("/admin/{userId}")
    public ResponseEntity<DadosUsuarioDTO> adminBuscarUsuario(@PathVariable String userId) {
        return ResponseEntity.ok(service.adminBuscarUsuarioPorID(userId));
    }

    @PatchMapping("/admin/{userId}")
    public ResponseEntity<DadosUsuarioDTO> adminAtualizarUsuario(@PathVariable String userId, @RequestBody AtualizarSenhaOutroUsuarioDTO dados) {
        return ResponseEntity.ok(service.adminAtualizarSenhaOutrosUsuarios(userId, dados));
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String userId) {
        service.removerUsuario(userId);
        return ResponseEntity.noContent().build();
    }
}