package br.com.karol.sistema.unit.domain.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.business.formatters.CpfFormatter;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.formatter.CpfFormatterImpl;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class CpfFormatterTest {

    private final String CPF_FORMATADO = TestConstants.CPF_FORMATADO;
    private final CpfFormatter formatter = new CpfFormatterImpl();


    @Test
    void testeFormatarCpf_comPontosETraco() {
        String cpfFormatado = formatter.format(CPF_FORMATADO);
        assertEquals(CPF_FORMATADO, cpfFormatado);
    }

    @Test
    void testeFormatarCpf_semPontosETraco() {
        String cpf = "12345678901";
        String cpfFormatado = formatter.format(cpf);
        assertEquals(CPF_FORMATADO, cpfFormatado);
    }

    @Test
    void testeFormatarCpf_vazio() {
        String cpf = "";
        assertThrows(FieldValidationException.class, () -> formatter.format(cpf));
    }

    @Test
    void testeFormatarCpf_nulo() {
        assertThrows(NullPointerException.class, () -> formatter.format(null));
    }

    @Test
    void testeFormatarCpf_invalido() {
        // Supondo um CPF inválido
        String cpf = "123";
        assertThrows(FieldValidationException.class, () -> formatter.format(cpf));
    }
}