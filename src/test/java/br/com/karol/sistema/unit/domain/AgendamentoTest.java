package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.validator.agendamento.AgendamentoValidator;

@ExtendWith(MockitoExtension.class)
public class AgendamentoTest {

    private final String OBSERVACAO = "observacao";
    private final StatusAgendamento STATUS = StatusAgendamento.PENDENTE;
    private final LocalDateTime DATA_HORA = LocalDateTime.now().plusDays(1);
    private final Procedimento PROCEDIMENTO = new Procedimento();
    private final Cliente CLIENTE = new Cliente();
    private final String USUARIO_LOGIN = "usuarioLogin";

    private final LocalDateTime PASSADO = LocalDateTime.now().minusDays(1);

    @Mock
    private List<AgendamentoValidator> validators;
    @Mock
    private AgendamentoValidator validator;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
    }


    @Test
    void testDeveCriarAgendamento() {
        Agendamento agendamento = new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, validators);

        assertNotNull(agendamento.getCliente());
        assertNotNull(agendamento.getDataCriacao());
        assertNotNull(agendamento.getDataHora());
        assertNotNull(agendamento.getDataModificacao());
        assertNotNull(agendamento.getObservacao());
        assertNotNull(agendamento.getProcedimento());
        assertNotNull(agendamento.getStatus());
        assertNull(agendamento.getUsuarioLogin());
    }

    @Test
    void testDeveCriarAgendamentoComSegundoConstrutor() {
        assertDoesNotThrow(() -> {
            Agendamento agendamento = new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, validators, USUARIO_LOGIN);

            assertNotNull(agendamento.getCliente());
            assertNotNull(agendamento.getDataCriacao());
            assertNotNull(agendamento.getDataHora());
            assertNotNull(agendamento.getDataModificacao());
            assertNotNull(agendamento.getObservacao());
            assertNotNull(agendamento.getProcedimento());
            assertNotNull(agendamento.getStatus());
            assertNotNull(agendamento.getUsuarioLogin());
        });
    }

    @Test
    void testDeveLancarExcecaoAoCriarClienteComAtributosNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(null, null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(null, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, validators));
        assertThrows(NullPointerException.class, 
            () -> new Agendamento(PROCEDIMENTO, null, OBSERVACAO, CLIENTE, DATA_HORA, validators));
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, null, DATA_HORA, validators));
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, null, validators));
        assertThrows(NullPointerException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, null));
    }

    @Test
    void testDeveLancarExcecaoAoCriarClienteComUsuarioLoginNullOuBlankNoSegundoConstrutor() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, validators, null));
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, validators, ""));
    }


    @Test
    void testNaoDeveCriarAgendamentoComStatusInvalido() {
        for (StatusAgendamento status : StatusAgendamento.values()) {
            if (status != StatusAgendamento.PENDENTE && status != StatusAgendamento.CONFIRMADO) {
                assertThrows(IllegalArgumentException.class, 
                    () -> new Agendamento(PROCEDIMENTO, status, OBSERVACAO, CLIENTE, DATA_HORA, validators));
            }
        }
    }

    @Test
    void testDeveCriarAgendamentoComStatusInvalido() {
        for (StatusAgendamento status : StatusAgendamento.values()) {
            if (status == StatusAgendamento.PENDENTE || status == StatusAgendamento.CONFIRMADO) {
                assertDoesNotThrow(
                    () -> new Agendamento(PROCEDIMENTO, status, OBSERVACAO, CLIENTE, DATA_HORA, validators));
            }
        }
    }

    @Test
    void testDeveCriarAgendamentoComObservacaoNullEBlank() {
        assertDoesNotThrow(() -> {
            var result = new Agendamento(PROCEDIMENTO, STATUS, null, CLIENTE, DATA_HORA, validators);

            assertNotNull(result.getObservacao()); // valida se observacao não é null
            assertTrue(result.getObservacao().isBlank()); // valida se observacao é blank
        });

        assertDoesNotThrow(() -> new Agendamento(PROCEDIMENTO, STATUS, "", CLIENTE, DATA_HORA, validators));
    }

    @Test
    void testNaoDeveCriarAgendamentoComDataPassada() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, PASSADO, validators));
    }

    @Test
    void testDeveAlterarObservacao() {
        // arrange
        Agendamento test = new Agendamento();
        var observacaoOriginal = test.getObservacao();

        // act and assert
        assertDoesNotThrow(() -> test.setObservacao(OBSERVACAO));
        assertNotEquals(observacaoOriginal, test.getObservacao());
    }

    @Test
    void testDeveRemarcarAgendamento() {
        // arrange
        Agendamento test = new Agendamento();
        var dataHoraOriginal = test.getDataHora();

        // act and assert
        assertDoesNotThrow(() -> test.remarcar(DATA_HORA, validators));
        assertNotEquals(dataHoraOriginal, test.getDataHora());
    }

    @Test
    void testNaoDeveRemarcarAgendamentoQuandoDataInvalida() {
        // arrange
        Agendamento test = new Agendamento();
        
        // act and assert
        assertThrows(IllegalArgumentException.class, () -> test.remarcar(PASSADO, validators));
    }
}