package br.com.karol.sistema.unit.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.karol.sistema.api.dto.usuario.AtualizarNomeUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaUsuarioDTO;
import br.com.karol.sistema.api.factory.SenhaFactory;
import br.com.karol.sistema.api.mapper.UsuarioMapper;
import br.com.karol.sistema.builder.UsuarioTestFactory;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import br.com.karol.sistema.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    private static final Usuario DEFAULT = UsuarioTestFactory.getUsuarioAdmin();

    @Mock
    private UsuarioRepository repository;
    @Mock
    private UsuarioMapper mapper;
    @Mock
    private SenhaFactory senhaMapper;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsuarioService service;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;


    @Test
    void testAtualizarNomeUsuarioAtual() {
        // arrange
        Usuario usuario = DEFAULT;
        AtualizarNomeUsuarioDTO dto = new AtualizarNomeUsuarioDTO("novo nome");
        
        when(mapper.toDadosUsuarioDTO(any())).thenReturn(null);

        // act
        service.atualizarNomeUsuarioAtual(usuario, dto);
        verify(repository).save(usuarioCaptor.capture());
        Usuario result = usuarioCaptor.getValue();
        
        // assert
        assertEquals(dto.getNome(), result.getNome());
    }

    @Test
    void testAtualizarSenhaUsuarioAtual() {
        // arrange
        Usuario usuario = DEFAULT;
        AtualizarSenhaUsuarioDTO dto = new AtualizarSenhaUsuarioDTO(
            "senha antiga",
            "nova senha"
        );
        
        when(senhaMapper.criarSenha(dto.getNovaSenha()))
            .thenReturn(
                UsuarioUtils.getSenha(
                    dto.getNovaSenha()));
                    
        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // act
        service.atualizarSenhaUsuarioAtual(usuario, dto);
        verify(repository).save(usuarioCaptor.capture());
        Usuario result = usuarioCaptor.getValue();
        
        // assert
        assertEquals(dto.getNovaSenha(), result.getSenha());
    }

    @Test
    void testAdminAtualizarSenhaOutrosUsuario() {
        // arrange
        Usuario usuario = DEFAULT;
        Long USUARIO_ID = usuario.getId();
        var entry = new AtualizarSenhaOutroUsuarioDTO("nova senha");
        
        when(senhaMapper.criarSenha(entry.getSenha()))
            .thenReturn(
                UsuarioUtils.getSenha(
                    entry.getSenha()));

        when(repository.findById(USUARIO_ID)).thenReturn(Optional.of(usuario));

        // act
        service.adminAtualizarSenhaOutrosUsuarios(USUARIO_ID, entry);
        verify(repository).save(usuarioCaptor.capture());
        Usuario result = usuarioCaptor.getValue();
        
        // assert
        assertEquals(entry.getSenha(), result.getSenha());
    }

    @Test
    void testAdminRemoverUsuario_usuarioInexistente() {
        // arrange
        Long USUARIO_ID = DEFAULT.getId();
        
        when(repository.existsById(USUARIO_ID)).thenReturn(false);

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.adminRemoverUsuario(USUARIO_ID));

        verify(repository).existsById(USUARIO_ID);
        verifyNoMoreInteractions(repository);
    }
}