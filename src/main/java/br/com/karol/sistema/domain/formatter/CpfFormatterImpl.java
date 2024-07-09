package br.com.karol.sistema.domain.formatter;

import org.springframework.stereotype.Component;

import br.com.caelum.stella.format.CPFFormatter;

@Component
public class CpfFormatterImpl implements CpfFormatter {

    @Override
    public String format(String value) {
        value = value
            .replace(".", "")
            .replace(".", "")
            .replace("-", "");
        return new CPFFormatter().format(value);
    }
}