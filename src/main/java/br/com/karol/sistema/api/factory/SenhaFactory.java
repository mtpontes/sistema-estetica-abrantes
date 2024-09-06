package br.com.karol.sistema.api.factory;


import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Senha;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SenhaFactory {

    private final SenhaValidator validators;
    private final SenhaEncoder formatter;

    
    public Senha criarSenha(String value) {
        return new Senha(value, validators, formatter);
    }
}