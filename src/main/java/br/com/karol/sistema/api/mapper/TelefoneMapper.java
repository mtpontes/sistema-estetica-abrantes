package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validations.cliente.telefone.TelefoneValidator;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TelefoneMapper {

    private final List<TelefoneValidator> validators;
    private final TelefoneFormatter formatter;

    
    public Telefone toTelefone(String value) {
        return new Telefone(value, validators, formatter);
    }

    public Telefone toTelefoneOrNull(String value) {
        if (value != null && !value.isBlank())
            return new Telefone(value, validators, formatter);
        else return null;
    }
}