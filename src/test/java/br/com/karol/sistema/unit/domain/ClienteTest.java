package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import br.com.karol.sistema.unit.utils.ClienteUtils;

public class ClienteTest {

    private final String NOME = "nome";
    private final Cpf CPF = new Cpf();
    private final Telefone TELEFONE = new Telefone();
    private final Email EMAIL = new Email();
    private final Endereco ENDERECO = new Endereco();

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(CPF, "value", "12345678999");
        ReflectionTestUtils.setField(TELEFONE, "value", "12345678999");
        ReflectionTestUtils.setField(EMAIL, "value", "email_san@email.com");
        ReflectionTestUtils.setField(ENDERECO, "rua", "dos bobos");
    }


    @Test
    void testDeveCriarClienteComAtributosValidos() {
        Cliente result = new Cliente(NOME, CPF, TELEFONE, EMAIL, ENDERECO);

        assertNotNull(result.getNome());
        assertNotNull(result.getTelefone());
        assertNotNull(result.getEmail());
        assertNotNull(result.getEndereco());
    }

    @Test
    void testDeveLancarExcecaoAoCriarClienteComAtributosNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(null, CPF, TELEFONE, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(NOME, null, TELEFONE, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(NOME, CPF, null, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(NOME, CPF, TELEFONE, null, ENDERECO));
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente(NOME, CPF, TELEFONE, EMAIL, null));
    }

    @Test
    void testDeveLancarExcecaoAoCriarClienteComAtributosNomeBlank() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Cliente("", CPF, TELEFONE, EMAIL, ENDERECO));
    }

    @Test
    void testDeveAtualizarDadosCliente() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();
        var nomeOriginal = cliente.getNome();
        var telefoneOriginal = cliente.getTelefone();
        var emailOriginal = cliente.getEmail();

        // act and assert
        assertDoesNotThrow(() -> cliente.atualizarDados(NOME, TELEFONE, EMAIL));
        
        assertNotEquals(nomeOriginal, cliente.getNome());
        assertNotEquals(telefoneOriginal, cliente.getTelefone());
        assertNotEquals(emailOriginal, cliente.getEmail());
    }

    @Test
    void testNaoDeveAtualizarDadosClienteQuandoValoresForemNull() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();
        var nomeOriginal = cliente.getNome();
        var telefoneOriginal = cliente.getTelefone();
        var emailOriginal = cliente.getEmail();

        // act and assert
        assertDoesNotThrow(() -> cliente.atualizarDados(null, null, null));
        
        assertEquals(nomeOriginal, cliente.getNome());
        assertEquals(telefoneOriginal, cliente.getTelefone());
        assertEquals(emailOriginal, cliente.getEmail());
    }

    @Test
    void testDeveAtualizarEndereco() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();
        var enderecoOriginal = cliente.getEndereco();
        var novoEndereco = new Endereco();
        ReflectionTestUtils.setField(novoEndereco, "rua", "umaRua");
        
        // act and assert
        assertDoesNotThrow(() -> cliente.atualizarEndereco(novoEndereco));
        assertNotEquals(enderecoOriginal, cliente.getEndereco());
    }

    @Test
    void testNaoDeveAtualizarEndereco() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();
        var enderecoOriginal = cliente.getEndereco();

        // act and assert
        assertDoesNotThrow(() -> cliente.atualizarEndereco(null));
        assertEquals(enderecoOriginal, cliente.getEndereco());
    }

    @Test
    void testGetCpfDeveRetornarString() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getCpf() instanceof String);
    }
    @Test
    void testGetTelefoneDeveRetornarString() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getTelefone() instanceof String);
    }
    @Test
    void testGetEmailDeveRetornarString() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getEmail() instanceof String);
    }
}