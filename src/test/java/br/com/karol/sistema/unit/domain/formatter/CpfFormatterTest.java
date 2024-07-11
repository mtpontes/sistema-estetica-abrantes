package br.com.karol.sistema.unit.domain.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.formatter.CpfFormatterImpl;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class CpfFormatterTest {

    private final String CPF_FORMATADO = "123.456.789-01";
    private final CpfFormatter formatter = new CpfFormatterImpl();


    @Test
    void testeFormatarCpfComPontosETraço() {
        String cpfFormatado = formatter.format(CPF_FORMATADO);
        assertEquals(CPF_FORMATADO, cpfFormatado);
    }

    @Test
    void testeFormatarCpfSemPontosETraço() {
        String cpf = "12345678901";
        String cpfFormatado = formatter.format(cpf);
        assertEquals(CPF_FORMATADO, cpfFormatado);
    }

    @Test
    void testeFormatarCpfVazio() {
        String cpf = "";
        assertThrows(FieldValidationException.class, () -> formatter.format(cpf));
    }

    @Test
    void testeFormatarCpfNulo() {
        assertThrows(NullPointerException.class, () -> formatter.format(null));
    }

    @Test
    void testeFormatarCpfInvalido() {
        // Supondo um CPF inválido
        String cpf = "123";
        assertThrows(FieldValidationException.class, () -> formatter.format(cpf));
    }
}