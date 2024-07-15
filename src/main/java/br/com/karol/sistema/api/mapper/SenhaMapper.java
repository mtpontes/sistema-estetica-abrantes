package br.com.karol.sistema.api.mapper;


import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.usuario.senha.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Senha;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SenhaMapper {

    private final SenhaValidator validators;
    private final SenhaEncoder formatter;

    
    public Senha toSenha(String value) {
        return new Senha(value, validators, formatter);
    }
}