package br.com.karol.sistema.unit.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.mapper.ProcedimentoMapper;
import br.com.karol.sistema.business.service.ProcedimentoService;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;
import br.com.karol.sistema.unit.utils.ProcedimentoUtils;

@ExtendWith(MockitoExtension.class)
public class ProcedimentoServiceTest {

    private static final Procedimento DEFAULT = ProcedimentoUtils.getProcedimento();

    @Mock
    private ProcedimentoRepository repository;
    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private ProcedimentoService service;

    @BeforeEach
    void setup() {
        ProcedimentoMapper mapper = new ProcedimentoMapper();
        this.service = new ProcedimentoService(repository, mapper, agendamentoRepository);
    }


    @Test
    void testSalvarProcedimento_comNomeJaExistente() {
        // arrange
        CriarProcedimentoDTO entry = new CriarProcedimentoDTO();
        when(repository.existsByNome(entry.getNome())).thenReturn(true);
        
        // act and assert
        assertThrows(FieldValidationException.class, () -> service.salvarProcedimento(entry));

        verify(repository).existsByNome(entry.getNome());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testEditarProcedimento() {
        // arrange
        AtualizarProcedimentoDTO entry = new AtualizarProcedimentoDTO(            
            DEFAULT.getNome(),
            DEFAULT.getDescricao(),
            DEFAULT.getDuracao(),
            DEFAULT.getValor()
        );
        when(repository.existsByNome(entry.getNome())).thenReturn(false);
        when(repository.findById(any())).thenReturn(Optional.of(DEFAULT));
        when(repository.save(any())).thenReturn(DEFAULT);
        
        // act
        var result = service.editarProcedimento(1L, entry);

        // assert
        assertEquals(entry.getNome(), result.getNome());
        assertEquals(entry.getDescricao(), result.getDescricao());
        assertEquals(entry.getDuracao(), result.getDuracao());
        assertEquals(entry.getValor(), result.getValor());
    }
    @Test
    void testEditarProcedimento_comNomeJaExistente() {
        // arrange
        AtualizarProcedimentoDTO entry = new AtualizarProcedimentoDTO();            
        when(repository.existsByNome(entry.getNome())).thenReturn(true);

        // act and assert
        assertThrows(FieldValidationException.class, () -> service.editarProcedimento(1L, entry));

        verify(repository).existsByNome(entry.getNome());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testRemoverProcedimento_naoEncontrado() {
        // arrange
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        // act
        assertThrows(EntityNotFoundException.class, () -> service.removerProcedimento(id));

        // assert
        verify(repository).existsById(id);
        verifyNoMoreInteractions(repository);
    }
    @Test
    void testRemoverProcedimento_vinculadoAUmAgendamentoEmAberto() {
        // arrange
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        when(agendamentoRepository.existsByProcedimentoIdAndStatusIn(id)).thenReturn(true);

        // act
        assertThrows(IllegalArgumentException.class, () -> service.removerProcedimento(id));

        // assert
        verify(repository).existsById(id);
        verifyNoMoreInteractions(repository);
    }
}