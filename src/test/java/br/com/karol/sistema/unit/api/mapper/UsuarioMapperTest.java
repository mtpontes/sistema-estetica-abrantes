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
import br.com.karol.sistema.api.mapper.LoginMapper;
import br.com.karol.sistema.api.mapper.SenhaMapper;
import br.com.karol.sistema.api.mapper.UsuarioMapper;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.unit.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
public class UsuarioMapperTest {

    private static final Usuario DEFAULT = UsuarioUtils.getUsuario();

    @Mock
    private LoginMapper loginMapper;
    @Mock
    private SenhaMapper senhaMapper;

    @InjectMocks
    private UsuarioMapper mapper;


    @Test
    void testToUsuarioUser() {
        // arrange
        CriarUsuarioDTO dto = new CriarUsuarioDTO("nome", "login", "senha");
        when(loginMapper.toLogin(anyString())).thenReturn(UsuarioUtils.getLogin(dto.getLogin()));
        when(senhaMapper.toSenha(anyString())).thenReturn(UsuarioUtils.getSenha(dto.getSenha()));

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
        CriarUsuarioDTO dto = new CriarUsuarioDTO("nome", "login", "senha");
        when(loginMapper.toLogin(anyString())).thenReturn(UsuarioUtils.getLogin(dto.getLogin()));
        when(senhaMapper.toSenha(anyString())).thenReturn(UsuarioUtils.getSenha(dto.getSenha()));

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
        CriarUsuarioDTO dto = new CriarUsuarioDTO("nome", "login", "senha");
        when(loginMapper.toLogin(anyString())).thenReturn(UsuarioUtils.getLogin(dto.getLogin()));
        when(senhaMapper.toSenha(anyString())).thenReturn(UsuarioUtils.getSenha(dto.getSenha()));

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
        Usuario entry = DEFAULT;

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
        List<Usuario> entry = List.of(DEFAULT);

        // act
        List<DadosUsuarioDTO> result = mapper.toListDadosUsuarioDTO(entry);

        // assert
        assertEquals(entry.get(0).getId(), result.get(0).getId());
        assertEquals(entry.get(0).getNome(), result.get(0).getNome());
        assertEquals(entry.get(0).getRole(), result.get(0).getRole());
    }
}