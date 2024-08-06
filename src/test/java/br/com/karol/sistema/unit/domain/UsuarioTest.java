package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.builder.UsuarioBuilder;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.unit.utils.UsuarioUtils;

public class UsuarioTest {

    private static final Usuario user = new UsuarioBuilder().login("").senha("").build();
    private final String NOME = "Nome";
    private final Login LOGIN = UsuarioUtils.getLogin();
    private final Senha SENHA = UsuarioUtils.getSenha();


    @Test
    void testCriarUsuarioComValoresValidos() {
        Usuario result = new Usuario(NOME, LOGIN, SENHA);

        assertNotNull(result.getNome());
        assertNotNull(result.getLogin());
        assertNotNull(result.getSenha());
    }

    @Test
    void testCriarUsuarioComValoresNull() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario(null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Usuario(NOME, null, SENHA));
        assertThrows(IllegalArgumentException.class, () -> new Usuario(NOME, LOGIN, null));
    }

    @Test
    void testCriarUsuarioComStringBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Usuario("", LOGIN, SENHA));
    }
    
    @Test
    void testDeveAtualizarDadosUsuario() {
        // arrange
        Usuario test = user;
        String nomeAtual = test.getNome();

        // act and assert
        assertDoesNotThrow(() -> test.atualizarDados(NOME));
        assertNotEquals(nomeAtual, test.getNome());
    }
    
    @Test
    void testNaoDeveAtualizarDadosUsuarioComValoresInvalidos() {
        // act and assert
        Usuario test = user;

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> test.atualizarDados(null));
        assertThrows(IllegalArgumentException.class, () -> test.atualizarDados(""));
    }

    @Test
    void testDeveAtualizarSenhaUsuario() {
        // arrange
        Usuario test = user;
        String senhaOriginal = test.getSenha();

        // act and assert
        assertDoesNotThrow(() -> test.atualizarSenha(UsuarioUtils.getSenha("novaSenha")));
        assertNotEquals(senhaOriginal, test.getSenha());
    }

    @Test
    void testAtualizarSenhaUsuarioComValorNullDeveLancarException() {
        // arrange
        Usuario test = user;

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> test.atualizarSenha(null));
    }
    
    @Test
    void testGetLoginDeveRetornarString() {
        // arrange
        Usuario user = UsuarioUtils.getUsuario();

        // act and assert
        assertTrue(user.getLogin() instanceof String);
    }

    @Test
    void testGetSenhaDeveRetornarString() {
        // assert
        Usuario user = UsuarioUtils.getUsuario();

        // act and assert
        assertTrue(user.getSenha() instanceof String);
    }
}