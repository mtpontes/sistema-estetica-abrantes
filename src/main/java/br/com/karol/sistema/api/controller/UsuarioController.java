package br.com.karol.sistema.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.api.dto.authentication.LoginResponseDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarNomeUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.domain.Usuario;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final TokenService tokenService;


    // -- Rotas comuns --

    @PostMapping
    public ResponseEntity<DadosUsuarioDTO> criarUsuario(
        @RequestBody @Valid CriarUsuarioDTO dados
    ) {
        return ResponseEntity.ok(service.salvarUsuario(dados));
    }

    /* O usuário padrão precisa se autenticar e só consegue consultar os seus próprios dados */
    @GetMapping
    public ResponseEntity<DadosUsuarioDTO> buscarUsuarioPorAutenticacao(
        Authentication authentication
    ) {
        return ResponseEntity.ok(
            service.getDadosUsuarioAtual((Usuario) authentication.getPrincipal()));
    }

    @PatchMapping("/nome")
    public ResponseEntity<DadosUsuarioDTO> atualizarNomeUsuario(
        Authentication authentication, 
        @RequestBody @Valid AtualizarNomeUsuarioDTO dados
    ) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(
            service.atualizarNomeUsuarioAtual(usuario, dados));
    }

    @PatchMapping("/senha")
    public ResponseEntity<LoginResponseDTO> atualizarSenhaUsuarioAtual(
        Authentication authentication,
        @RequestBody @Valid AtualizarSenhaUsuarioDTO dados
    ) {
        Usuario usuarioAtual = (Usuario) authentication.getPrincipal();
        Usuario usuarioValidado = 
            service.atualizarSenhaUsuarioAtual(usuarioAtual, dados);
        String token = this.tokenService.generateToken(usuarioValidado.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    

    // --- Rotas de ADMIN -- 

    @PostMapping("/admin")
    public ResponseEntity<DadosUsuarioDTO> adminCriarUsuario(
        @RequestBody @Valid CriarUsuarioDTO dados
    ) {
        return ResponseEntity.ok(service.adminSalvarUsuario(dados));
    }
    
    @GetMapping("/admin")
    public ResponseEntity<List<DadosUsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(service.adminListarTodosUsuarios());
    }

    /* Um usuário com ROLE ADMIN autenticado consegue recuperar dados de outros usuários */
    @GetMapping("/admin/{userId}")
    public ResponseEntity<DadosUsuarioDTO> adminBuscarUsuario(
        @PathVariable Long userId
    ) {
        return ResponseEntity.ok(service.adminBuscarUsuarioPorID(userId));
    }

    @PatchMapping("/admin/{userId}")
    public ResponseEntity<DadosUsuarioDTO> adminAtualizarSenhaUsuario(
        @PathVariable Long userId, 
        @RequestBody @Valid AtualizarSenhaOutroUsuarioDTO dados
    ) {
        return ResponseEntity.ok(
            service.adminAtualizarSenhaOutrosUsuarios(userId, dados));
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long userId) {
        service.adminRemoverUsuario(userId);
        return ResponseEntity.noContent().build();
    }
}