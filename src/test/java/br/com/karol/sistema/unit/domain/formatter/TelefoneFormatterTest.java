package br.com.karol.sistema.unit.domain.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.business.formatters.TelefoneFormatterImpl;
import br.com.karol.sistema.domain.formatter.TelefoneFormatter;

public class TelefoneFormatterTest {

    private final TelefoneFormatter formatter = new TelefoneFormatterImpl();


    @Test
    void testFormatTelefone_comSucesso() {
        String telefone = "11999999999";
        String telefoneFormatado = formatter.format(telefone);
        assertEquals("(11) 99999-9999", telefoneFormatado);
    }
}