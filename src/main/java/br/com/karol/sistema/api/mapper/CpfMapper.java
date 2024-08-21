package br.com.karol.sistema.api.mapper;


import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CpfMapper {

    private final List<CpfValidator> validators;
    private final CpfFormatter formatter;

    
    public Cpf toCpf(String value) {
        return new Cpf(value, validators, formatter);
    }

    public Cpf toCpfOrNull(String value) {
        if (value != null && !value.isBlank())
            return new Cpf(value, validators, formatter);
        else return null;
    }
}