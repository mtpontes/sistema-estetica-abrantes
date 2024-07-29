package br.com.karol.sistema.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.api.dto.authentication.AuthenticationDTO;
import br.com.karol.sistema.api.dto.authentication.LoginResponseDTO;
import br.com.karol.sistema.business.service.TokenService;
import br.com.karol.sistema.business.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    
    @PostMapping
    public ResponseEntity<LoginResponseDTO> fazerLogin(@RequestBody @Valid AuthenticationDTO data) {
        var token = tokenService.generateToken(usuarioService.autenticarUsuario(
                    data.getLogin(), 
                    data.getPassword()
                )
            );
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}