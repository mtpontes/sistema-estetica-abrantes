package br.com.karol.sistema.api.factory;


import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CpfFactory {

    private final List<CpfValidator> validators;
    private final CpfFormatter formatter;

    
    public Cpf createCpf(String value) {
        return new Cpf(value, validators, formatter);
    }

    public Cpf toCpfOrNull(String value) {
        if (value != null && !value.isBlank())
            return new Cpf(value, validators, formatter);
        else return null;
    }
}