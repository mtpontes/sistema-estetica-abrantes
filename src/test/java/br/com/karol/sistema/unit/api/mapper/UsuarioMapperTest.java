package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.api.factory.UsuarioFactory;
import br.com.karol.sistema.api.mapper.UsuarioMapper;
import br.com.karol.sistema.builder.UsuarioTestFactory;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;

@ExtendWith(MockitoExtension.class)
public class UsuarioMapperTest {

    private static final Usuario default_user = UsuarioTestFactory.getUsuarioUser();
    private static final Usuario default_admin = UsuarioTestFactory.getUsuarioAdmin();
    private static final Usuario default_client = UsuarioTestFactory.getUsuarioClient();

    @Mock
    private UsuarioFactory usuarioFactory;

    @InjectMocks
    private UsuarioMapper mapper;


    @Test
    void testToUsuarioUser() {
        // arrange
        CriarUsuarioDTO dto = new CriarUsuarioDTO(default_user.getNome(), default_user.getLogin(), default_user.getSenha());
        when(usuarioFactory.criarUsuarioUser(anyString(), anyString(), anyString()))
            .thenReturn(default_user);

        // act
        Usuario result = mapper.toUsuarioUser(dto);

        // assert
        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getLogin(), result.getLogin());
        assertEquals(dto.getSenha(), result.getSenha());
        assertEquals(UserRole.USER, result.getRole());
    }

    @Test
    void testToUsuarioAdmin() {
        // arrange
        CriarUsuarioDTO dto = new CriarUsuarioDTO(default_admin.getNome(), default_admin.getLogin(), default_admin.getSenha());
        when(usuarioFactory.criarUsuarioAdmin(anyString(), anyString(), anyString()))
            .thenReturn(default_admin);
        System.out.println("USUARIO: " + default_user);

        // act
        Usuario result = mapper.toUsuarioAdmin(dto);

        // assert
        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getLogin(), result.getLogin());
        assertEquals(dto.getSenha(), result.getSenha());
        assertEquals(UserRole.ADMIN, result.getRole());
    }

    @Test
    void testToUsuarioClient() {
        // arrange
        CriarUsuarioDTO dto = new CriarUsuarioDTO(default_client.getNome(), default_client.getLogin(), default_client.getSenha());
        when(usuarioFactory.criarUsuarioClient(anyString(), anyString(), anyString()))
            .thenReturn(default_client);

        // act
        Usuario result = mapper.toUsuarioClient(dto);

        // assert
        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getLogin(), result.getLogin());
        assertEquals(dto.getSenha(), result.getSenha());
        assertEquals(UserRole.CLIENT, result.getRole());
    }

    @Test
    void testToDadosUsuarioDTO() {
        // arrange
        Usuario entry = default_admin;

        // act
        DadosUsuarioDTO result = mapper.toDadosUsuarioDTO(entry);

        // assert
        assertEquals(entry.getId(), result.getId());
        assertEquals(entry.getNome(), result.getNome());
        assertEquals(entry.getRole(), result.getRole());
    }

    @Test
    void testToListDadosUsuarioDTO() {
        // arrange
        List<Usuario> entry = List.of(default_admin);

        // act
        List<DadosUsuarioDTO> result = mapper.toListDadosUsuarioDTO(entry);

        // assert
        assertEquals(entry.get(0).getId(), result.get(0).getId());
        assertEquals(entry.get(0).getNome(), result.get(0).getNome());
        assertEquals(entry.get(0).getRole(), result.get(0).getRole());
    }
}