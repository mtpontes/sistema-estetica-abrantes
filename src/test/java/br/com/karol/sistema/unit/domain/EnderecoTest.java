package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void testDeveCriarEnderecoComAtributosValidos() {
        Endereco result = new Endereco(RUA, NUMERO, CIDADE, BAIRRO, ESTADO);

        assertNotNull(result.getRua());
        assertNotNull(result.getNumero());
        assertNotNull(result.getCidade());
        assertNotNull(result.getBairro());
        assertNotNull(result.getEstado());
    }

    @Test
    void testDeveLancarNullPointerExceptionAoCriarEnderecoComCamposNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(null, NUMERO, CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, null, CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, null, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, CIDADE, null, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, CIDADE, BAIRRO, null));
    }

    @Test
    void testDeveLancarIllegalArgumentExceptionAoCriarEnderecoComCamposBlank() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco("", "", "", "", ""));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco("", NUMERO, CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, "", CIDADE, BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, "", BAIRRO, ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, CIDADE, "", ESTADO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Endereco(RUA, NUMERO, CIDADE, BAIRRO, ""));
    }
}