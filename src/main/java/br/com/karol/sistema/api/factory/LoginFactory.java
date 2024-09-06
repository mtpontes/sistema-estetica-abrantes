package br.com.karol.sistema.api.factory;


import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.validator.LoginValidator;
import br.com.karol.sistema.domain.valueobjects.Login;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LoginFactory {

    private final List<LoginValidator> validators;

    
    public Login criarLogin(String value) {
        return new Login(value, validators);
    }
}