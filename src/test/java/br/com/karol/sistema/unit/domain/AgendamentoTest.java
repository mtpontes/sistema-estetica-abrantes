package br.com.karol.sistema.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.StatusAgendamento;

public class AgendamentoTest {

    private final String OBSERVACAO = "observacao";
    private final StatusAgendamento STATUS = StatusAgendamento.PENDENTE;
    private final LocalDateTime DATA_HORA = LocalDateTime.now().plusDays(1);
    private final Procedimento PROCEDIMENTO = new Procedimento();
    private final Cliente CLIENTE = new Cliente();
    private final Usuario USUARIO = new Usuario();

    private final LocalDateTime PASSADO = LocalDateTime.now().minusDays(1);
    private final LocalDateTime FUTURO = LocalDateTime.now().plusDays(100);


    @Test
    void deveCriarAgendamentoTest() {
        assertDoesNotThrow(() -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, USUARIO));
    }

    @Test
    void deveLancarExcecaoAoCriarClienteComAtributosNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(null, null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(null, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, USUARIO));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(PROCEDIMENTO, null, OBSERVACAO, CLIENTE, DATA_HORA, USUARIO));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, null, DATA_HORA, USUARIO));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, null, USUARIO));
        assertThrows(IllegalArgumentException.class, () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, DATA_HORA, null));
    }

    @Test
    void naoDeveCriarAgendamentoComStatusInvalidoTest() {
        for (StatusAgendamento status : StatusAgendamento.values()) {
            if (status != StatusAgendamento.PENDENTE && status != StatusAgendamento.CONFIRMADO) {

                assertThrows(IllegalArgumentException.class, 
                    () -> new Agendamento(PROCEDIMENTO, status, OBSERVACAO, CLIENTE, DATA_HORA, USUARIO));
            }
        }
    }

    @Test
    void deveCriarAgendamentoComObservacaoNullEBlankTest() {
        assertDoesNotThrow(() -> {
            var result = new Agendamento(PROCEDIMENTO, STATUS, null, CLIENTE, DATA_HORA, USUARIO);

            assertNotNull(result.getObservacao()); // valida se observacao não é null
            assertTrue(result.getObservacao().isBlank()); // valida se observacao é blank
        });

        assertDoesNotThrow(() -> new Agendamento(PROCEDIMENTO, STATUS, "", CLIENTE, DATA_HORA, USUARIO));
    }

    @Test
    void naoDeveCriarAgendamentoComDataPassadaTest() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Agendamento(PROCEDIMENTO, STATUS, OBSERVACAO, CLIENTE, PASSADO, USUARIO));
    }

    @Test
    void deveRemarcarAgendamentoTest() {
        // arrange
        Agendamento test = new Agendamento();
        var observacaoOriginal = test.getObservacao();
        var dataHoraOriginal = test.getDataHora();

        // act and assert
        assertDoesNotThrow(() -> test.remarcarAgendamento(OBSERVACAO, FUTURO));
        
        assertNotEquals(observacaoOriginal, test.getObservacao());
        assertNotEquals(dataHoraOriginal, test.getDataHora());
    }

    @Test
    void naoDeveRemarcarAgendamentoQuandoDataInvalidaTest() {
        // arrange
        Agendamento test = new Agendamento();
        
        // act and assert
        assertThrows(IllegalArgumentException.class, () -> test.remarcarAgendamento(OBSERVACAO, PASSADO));
    }

    @Test
    void deveRemarcarAgendamentoSemAlterarDataQuandoNovaDataForNullTest() {
        // arrange
        Agendamento test = new Agendamento();
        var dataOriginal = test.getDataHora();

        // act and assert
        assertDoesNotThrow(() -> test.remarcarAgendamento(null, null));

        assertTrue(test.getObservacao().isBlank());
        assertEquals(dataOriginal, test.getDataHora());
    }
}