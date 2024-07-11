package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


    @Test
    void deveCriarClienteComAtributosValidosTest() {
        assertDoesNotThrow(() -> new Cliente(NOME, CPF, TELEFONE, EMAIL, ENDERECO));
    }

    @Test
    void deveLancarExcecaoAoCriarClienteComAtributosNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(null, CPF, TELEFONE, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(NOME, null, TELEFONE, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(NOME, CPF, null, EMAIL, ENDERECO));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(NOME, CPF, TELEFONE, null, ENDERECO));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(NOME, CPF, TELEFONE, EMAIL, null));
    }

    @Test
    void deveLancarExcecaoAoCriarClienteComAtributosNomeBlankTest() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("", CPF, TELEFONE, EMAIL, ENDERECO));
    }

    @Test
    void deveAtualizarDadosClienteTest() {
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
    void naoDeveAtualizarDadosClienteQuandoValoresForemNullTest() {
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
    void deveAtualizarEnderecoTest() {
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
    void naoDeveAtualizarEnderecoTest() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();
        var enderecoOriginal = cliente.getEndereco();

        // act and assert
        assertDoesNotThrow(() -> cliente.atualizarEndereco(null));
        assertEquals(enderecoOriginal, cliente.getEndereco());
    }

    @Test
    void getCpfDeveRetornarStringTest() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getCpf() instanceof String);
    }
    @Test
    void getTelefoneDeveRetornarStringTest() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getTelefone() instanceof String);
    }
    @Test
    void getEmailDeveRetornarStringTest() {
        // arrange
        Cliente cliente = ClienteUtils.getCliente();

        // act and assert
        assertTrue(cliente.getEmail() instanceof String);
    }
}