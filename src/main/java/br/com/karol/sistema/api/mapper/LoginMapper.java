package br.com.karol.sistema.api.mapper;


import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.validator.usuario.login.LoginValidator;
import br.com.karol.sistema.domain.valueobjects.Login;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LoginMapper {

    private final List<LoginValidator> validators;

    
    public Login toLogin(String value) {
        return new Login(value, validators);
    }
}