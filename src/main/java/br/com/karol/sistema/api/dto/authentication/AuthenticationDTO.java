package br.com.karol.sistema.api.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationDTO {
    
    private String login;
    private String password;
}