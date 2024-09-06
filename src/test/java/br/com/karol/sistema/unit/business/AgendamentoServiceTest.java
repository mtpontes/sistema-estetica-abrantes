package br.com.karol.sistema.unit.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.api.dto.agendamento.AtualizarObservacaoAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.AtualizarStatusAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.RemarcarAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.builder.AgendamentoTestFactory;
import br.com.karol.sistema.builder.UsuarioTestFactory;
import br.com.karol.sistema.business.service.AgendamentoService;
import br.com.karol.sistema.business.service.ClienteService;
import br.com.karol.sistema.business.service.ProcedimentoService;
import br.com.karol.sistema.constants.TestConstants;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.validator.AgendamentoValidator;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    private static final Agendamento DEFAULT = AgendamentoTestFactory.getAgendamento();

    @Mock
    private AgendamentoRepository repository;
    @Mock
    private ClienteService clienteService;
    @Mock
    private ProcedimentoService procedimentoService;
    @Mock
    private AgendamentoMapper mapper;
    @Mock
    private List<AgendamentoValidator> validators;

    @InjectMocks
    private AgendamentoService service;

    @Captor
    private ArgumentCaptor<Agendamento> agendamentoCaptor;


    @Test
    void testBuscarAgendamentoPorId_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;

        when(repository.findById(agendamentoId))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class,
            () -> service.buscarAgendamentoPorId(agendamentoId));
        
        verifyNoInteractions(mapper);
    }
    @Test
    void testBuscarAgendamentoPorIdEUsuarioId_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        Long usuarioId = 1L;

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuarioId))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class,
            () -> service.buscarAgendamentoPorIdEUsuarioId(agendamentoId, usuarioId));

        // assert
        verifyNoInteractions(mapper);
    }

    @Test
    void testEditarDataHoraAgendamento() {
        // arrange
        Agendamento agendamento = DEFAULT;
        Long agendamentoId = agendamento.getId();
        RemarcarAgendamentoDTO entry = new RemarcarAgendamentoDTO(TestConstants.FUTURO);

        when(repository.findById(agendamentoId))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarDataHoraAgendamento(agendamentoId, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getDataHora(), result.getDataHora());
    }
    @Test
    void testEditarDataHoraAgendamento_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        RemarcarAgendamentoDTO entry = new RemarcarAgendamentoDTO(TestConstants.FUTURO);

        when(repository.findById(agendamentoId))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class,
            () -> service.editarDataHoraAgendamento(agendamentoId, entry));

        // assert
        verify(repository).findById(agendamentoId);
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @Test
    void testEditarDataHoraAgendamentoUsuarioAtual() {
        // arrange
        Agendamento agendamento = DEFAULT;
        Long agendamentoId = agendamento.getId();
        Long usuarioId = 1L;
        RemarcarAgendamentoDTO entry = new RemarcarAgendamentoDTO(TestConstants.FUTURO);

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuarioId))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarDataHoraAgendamentoUsuarioAtual(agendamentoId, usuarioId, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getDataHora(), result.getDataHora());
    }
    @Test
    void testEditarDataHoraAgendamentoUsuarioAtual_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        Long usuarioId = 1L;
        RemarcarAgendamentoDTO entry = new RemarcarAgendamentoDTO(TestConstants.FUTURO);

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuarioId))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.editarDataHoraAgendamentoUsuarioAtual(agendamentoId, usuarioId, entry));

        // assert
        verify(repository).findByIdAndClienteUsuarioId(agendamentoId, usuarioId);
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @Test
    void testEditarObservacaoAgendamento() {
        // arrange
        Agendamento agendamento = DEFAULT;
        Long agendamentoId = agendamento.getId();
        var entry = new AtualizarObservacaoAgendamentoDTO("nova descricao");

        when(repository.findById(agendamentoId))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarObservacaoAgendamento(agendamentoId, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getObservacao(), result.getObservacao());
    }
    @Test
    void testEditarObservacaoAgendamento_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        var entry = new AtualizarObservacaoAgendamentoDTO("nova descricao");

        when(repository.findById(agendamentoId)).thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.editarObservacaoAgendamento(agendamentoId, entry));

        // assert
        verify(repository).findById(agendamentoId);
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @Test
    void testEditarObservacaoAgendamentoUsuarioAtual() {
        // arrange
        Agendamento agendamento = DEFAULT;
        Long agendamentoId = agendamento.getId();
        Long usuarioId = 1L;
        var entry = new AtualizarObservacaoAgendamentoDTO("nova descricao");

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuarioId))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarObservacaoAgendamentoUsuarioAtual(agendamentoId, usuarioId, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getObservacao(), result.getObservacao());
    }
    @Test
    void testEditarObservacaoAgendamentoUsuarioAtual_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        Long usuarioId = 1L;
        var entry = new AtualizarObservacaoAgendamentoDTO("nova descricao");

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuarioId))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.editarObservacaoAgendamentoUsuarioAtual(agendamentoId, usuarioId, entry));

        // assert
        verify(repository).findByIdAndClienteUsuarioId(agendamentoId, usuarioId);
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @Test
    void testEditarStatusAgendamento() {
        // arrange
        Agendamento agendamento = DEFAULT;
        Long agendamentoId = agendamento.getId();
        var entry = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CANCELADO);

        when(repository.findById(agendamentoId))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarStatusAgendamento(agendamentoId, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getStatus(), result.getStatus());
    }
    @Test
    void testEditarStatusAgendamento_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;
        var entry = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);

        when(repository.findById(agendamentoId)).thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.editarStatusAgendamento(agendamentoId, entry));

        // assert
        verify(repository).findById(agendamentoId);
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @ParameterizedTest
    @CsvSource(value = {"ADMIN", "USER"})
    void testEditarStatusAgendamentoUsuarioAtual_comRoleValida(String role) {
        // arrange
        Agendamento agendamento = new Agendamento();
        ReflectionTestUtils.setField(agendamento, "status", StatusAgendamento.PENDENTE);

        Long agendamentoId = agendamento.getId();
        
        Usuario usuario = UsuarioTestFactory.getUsuarioAdmin();
        ReflectionTestUtils.setField(usuario, "role", UserRole.fromString(role));

        var entry = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuario.getId()))
            .thenReturn(Optional.of(agendamento));

        // act
        service.editarStatusAgendamentoUsuarioAtual(agendamentoId, usuario, entry);

        verify(repository).save(agendamentoCaptor.capture());
        Agendamento result = agendamentoCaptor.getValue();

        // assert
        assertEquals(entry.getStatus(), result.getStatus());
    }
    @Test
    void testEditarStatusAgendamentoUsuarioAtual_comRoleInvalida() {
        // arrange
        Agendamento agendamento = DEFAULT;

        Long agendamentoId = agendamento.getId();
        
        Usuario usuario = UsuarioTestFactory.getUsuarioAdmin();
        ReflectionTestUtils.setField(usuario, "role", UserRole.CLIENT);

        var entry = new AtualizarStatusAgendamentoDTO(StatusAgendamento.FINALIZADO);

        // act
        assertThrows(AccessDeniedException.class,
            () -> service.editarStatusAgendamentoUsuarioAtual(agendamentoId, usuario, entry)
        );
        
        verifyNoInteractions(repository);
    }
    @Test
    void testEditarStatusAgendamentoUsuarioAtual_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;

        Usuario usuario = UsuarioTestFactory.getUsuarioAdmin();
        ReflectionTestUtils.setField(usuario, "role", UserRole.ADMIN);

        var entry = new AtualizarStatusAgendamentoDTO(StatusAgendamento.CONFIRMADO);

        when(repository.findByIdAndClienteUsuarioId(agendamentoId, usuario.getId()))
            .thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.editarStatusAgendamentoUsuarioAtual(agendamentoId, usuario, entry));

        // assert
        verify(repository).findByIdAndClienteUsuarioId(agendamentoId, usuario.getId());
        verifyNoMoreInteractions(repository);

        verifyNoInteractions(mapper);
    }

    @Test
    void testRemoverAgendamento_naoEncontrado() {
        // arrange
        Long agendamentoId = 1L;

        when(repository.existsById(agendamentoId))
            .thenReturn(false);

        // act
        assertThrows(EntityNotFoundException.class, 
            () -> service.removerAgendamento(agendamentoId));

        // assert
        verify(repository).existsById(agendamentoId);
        verifyNoMoreInteractions(repository);
    }
}