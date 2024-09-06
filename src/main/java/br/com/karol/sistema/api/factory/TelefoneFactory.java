package br.com.karol.sistema.api.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.TelefoneValidator;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TelefoneFactory {

    private final List<TelefoneValidator> validators;
    private final TelefoneFormatter formatter;

    
    public Telefone createTelefone(String value) {
        return new Telefone(value, validators, formatter);
    }

    public Telefone toTelefoneOrNull(String value) {
        if (value != null && !value.isBlank())
            return new Telefone(value, validators, formatter);
        else return null;
    }
}