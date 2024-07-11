package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.Endereco;

public class EnderecoTest {

    private final String RUA = "rua";
    private final String NUMERO = "numero";
    private final String CIDADE = "cidade";
    private final String BAIRRO = "bairro";
    private final String ESTADO = "estado";


    @Test
    void deveCriarEnderecoComAtributosValidosTest() {
        assertDoesNotThrow(() -> new Endereco(RUA, NUMERO, CIDADE, BAIRRO, ESTADO));
    }

    @Test
    void deveLancarNullPointerExceptionAoCriarEnderecoComCamposNull() {
        assertThrows(NullPointerException.class, () -> new Endereco(null, null, null, null, null));
        assertThrows(NullPointerException.class, () -> new Endereco(null, NUMERO, CIDADE, BAIRRO, ESTADO));
        assertThrows(NullPointerException.class, () -> new Endereco(RUA, null, CIDADE, BAIRRO, ESTADO));
        assertThrows(NullPointerException.class, () -> new Endereco(RUA, NUMERO, null, BAIRRO, ESTADO));
        assertThrows(NullPointerException.class, () -> new Endereco(RUA, NUMERO, CIDADE, null, ESTADO));
        assertThrows(NullPointerException.class, () -> new Endereco(RUA, NUMERO, CIDADE, BAIRRO, null));
    }

    @Test
    void deveLancarIllegalArgumentExceptionAoCriarEnderecoComCamposBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Endereco("", "", "", "", ""));
        assertThrows(IllegalArgumentException.class, () -> new Endereco("", NUMERO, CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, () -> new Endereco(RUA, "", CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, () -> new Endereco(RUA, NUMERO, "", BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, () -> new Endereco(RUA, NUMERO, CIDADE, "", ESTADO));
        assertThrows(IllegalArgumentException.class, () -> new Endereco(RUA, NUMERO, CIDADE, BAIRRO, ""));
    }
}