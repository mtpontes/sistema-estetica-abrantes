package br.com.karol.sistema.domain.formatter;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.karol.sistema.business.formatters.CpfFormatter;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@Component
public class CpfFormatterImpl implements CpfFormatter {

    @Override
    public String format(String value) {
        value = value
            .replace(".", "")
            .replace(".", "")
            .replace("-", "");
        try {
            return new CPFFormatter().format(value);
            
        } catch (IllegalArgumentException ex) {
            throw new FieldValidationException(
                CPF.class.getSimpleName(), "formato inválido");
        }
    }
}