package br.com.karol.sistema.unit.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.mapper.ClienteMapper;
import br.com.karol.sistema.api.mapper.EmailMapper;
import br.com.karol.sistema.api.mapper.EnderecoMapper;
import br.com.karol.sistema.api.mapper.TelefoneMapper;
import br.com.karol.sistema.business.service.ClienteService;
import br.com.karol.sistema.business.service.UsuarioService;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import br.com.karol.sistema.unit.utils.ClienteUtils;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    private static final Cliente DEFAULT = ClienteUtils.getCliente();

    @Mock
    private ClienteRepository repository;
    @Mock
    private ClienteMapper mapper;
    @Mock
    private TelefoneMapper telefoneMapper;
    @Mock
    private EmailMapper emailMapper;
    @Mock
    private EnderecoMapper enderecoMapper;
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ClienteService service;

    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;


    @Test
    void testeEditarContatoCliente() {
        // arrange
        Cliente cliente = DEFAULT;
        Long clienteId = cliente.getId();

        var entry = new AtualizarClienteDTO(
            "novo nome",
            "novo telefone",
            "novo email"
        );

        when(repository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(telefoneMapper.toTelefoneOrNull(anyString()))
            .thenReturn(ClienteUtils.getTelefone(entry.getTelefone()));
        when(emailMapper.toEmailOrNull(anyString()))
            .thenReturn(ClienteUtils.getEmail(entry.getEmail()));

        // act
        service.editarContatoCliente(clienteId, entry);

        verify(repository).save(clienteCaptor.capture());
        Cliente result = clienteCaptor.getValue();

        // assert
        assertEquals(entry.getNome(), result.getNome());
        assertEquals(entry.getTelefone(), result.getTelefone());
        assertEquals(entry.getEmail(), result.getEmail());
    }

    @Test
    void testeEditarContatoClienteAtual() {
        // arrange
        Cliente cliente = DEFAULT;

        var entry = new AtualizarClienteDTO(
            "novo nome",
            "novo telefone",
            "novo email"
        );

        when(telefoneMapper.toTelefoneOrNull(anyString()))
            .thenReturn(ClienteUtils.getTelefone(entry.getTelefone()));
        when(emailMapper.toEmailOrNull(anyString()))
            .thenReturn(ClienteUtils.getEmail(entry.getEmail()));

        // act
        service.editarContatoClienteAtual(cliente, entry);

        verify(repository).save(clienteCaptor.capture());
        Cliente result = clienteCaptor.getValue();

        // assert
        assertEquals(entry.getNome(), result.getNome());
        assertEquals(entry.getTelefone(), result.getTelefone());
        assertEquals(entry.getEmail(), result.getEmail());
    }

    @Test
    void testEnderecoCliente() {
        // arrange
        Endereco entryAsEndereco = new Endereco(
            "nova rua",
            "123456789",
            "Cidade Nova",
            "Bairro novo",
            "Novo Estado"
        );
        EnderecoDTO entry = new EnderecoDTO(entryAsEndereco);

        Cliente cliente = DEFAULT;
        Long clienteId = cliente.getId();
        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente));
        
        when(enderecoMapper.toEndereco(entry)).thenReturn(entryAsEndereco);

        // act
        service.editarEnderecoCliente(clienteId, entry);

        verify(repository).save(clienteCaptor.capture());
        Cliente result = clienteCaptor.getValue();

        // assert
        assertEquals(entryAsEndereco, result.getEndereco());
    }

    @Test
    void testEnderecoClienteAtual() {
        // arrange
        Endereco entryAsEndereco = new Endereco(
            "nova rua",
            "123456789",
            "Cidade Nova",
            "Bairro novo",
            "Novo Estado"
        );
        EnderecoDTO entry = new EnderecoDTO(entryAsEndereco);

        Cliente cliente = DEFAULT;
        when(enderecoMapper.toEndereco(entry)).thenReturn(entryAsEndereco);

        // act
        service.editarEnderecoClienteAtual(cliente, entry);

        verify(repository).save(clienteCaptor.capture());
        Cliente result = clienteCaptor.getValue();

        // assert
        assertEquals(entryAsEndereco, result.getEndereco());
    }
    
    @Test
    void testRemoverCliente_naoEncontrado() {
        // arrange
        Long USUARIO_ID = 1L;
        when(repository.existsById(USUARIO_ID)).thenReturn(false);

        // act
        assertThrows(EntityNotFoundException.class,
            () -> service.removerCliente(USUARIO_ID));
        
        // assert
        verify(repository).existsById(USUARIO_ID);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testBuscarPorId_naoEncontrado() {
        // arrange
        Long USUARIO_ID = 1L;
        when(repository.findById(any())).thenReturn(Optional.empty());

        // act
        assertThrows(EntityNotFoundException.class,
            () -> service.buscarPorId(USUARIO_ID));
        
        // assert
        verify(repository).findById(USUARIO_ID);
        verifyNoMoreInteractions(repository);
    }
}