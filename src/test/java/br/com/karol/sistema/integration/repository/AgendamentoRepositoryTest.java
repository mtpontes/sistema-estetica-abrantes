package br.com.karol.sistema.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import br.com.karol.sistema.builder.ClienteBuilder;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;

@DataJpaTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private static final Pageable PAGEABLE = 
        PageRequest.of(0, 10);

    private Procedimento procedimento1;
    private Procedimento procedimento2;
    private Cliente cliente1;
    private Cliente cliente2;
    private Agendamento agendamento1;
    private Agendamento agendamento2;

    @BeforeEach
    void checkRollback() {
        assertTrue(agendamentoRepository.count() == 0L);
    }


    @Test
    void testExistsByProcedimentoIdAndStatusIn_comStatusFinalizado() {
        // arrange
        var procedimento = new Procedimento();
        procedimentoRepository.save(procedimento);

        var agendamentoFinalizado = Agendamento.builder()
            .procedimento(procedimento)
            .status(StatusAgendamento.FINALIZADO)
            .build();
        agendamentoRepository.save(agendamentoFinalizado);
        
        var agendamentoCancelado = Agendamento.builder()
            .procedimento(procedimento)
            .status(StatusAgendamento.CANCELADO)
            .build();
        agendamentoRepository.save(agendamentoCancelado);

        // act
        var resultFinalizado = agendamentoRepository
            .existsByProcedimentoIdAndStatusIn(1L);

        var resultCancelado = agendamentoRepository
            .existsByProcedimentoIdAndStatusIn(1L);

        // assert
        assertTrue(resultFinalizado);
        assertTrue(resultCancelado);
    }

    @Test
    void testFindAllByParams_semParams() {
        // arrange
        this.seedFindAllByParams();

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            PAGEABLE
        );

        var quantidadeEsperada = Long.valueOf(agendamentoRepository.count());

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comTodosParams() {
        // arrange
        this.seedFindAllByParams();
        var PROCEDIMENTO_ID = procedimento1.getId();
        var PROCEDIMENTO_NOME = procedimento1.getNome();
        var STATUS = agendamento1.getStatus();
        var MIN_DATAHORA = agendamento1.getDataHora().minusMinutes(1);
        var MAX_DATAHORA = agendamento1.getDataHora().plusMinutes(1);
        var CLIENTE_NOME = cliente1.getNome();
        var CLIENTE_ID = cliente1.getId();
        var CLIENTE_CPF = cliente1.getCpf();

        var match = agendamentoRepository.findAll().stream()
            .filter(a -> a.getProcedimento().getId() == PROCEDIMENTO_ID)
            .filter(a -> {
                return a.getProcedimento().getNome().equalsIgnoreCase(PROCEDIMENTO_NOME);
            })            
            .filter(a -> a.getStatus() == STATUS)
            .filter(a -> {
                return a.getDataHora() == MIN_DATAHORA ||
                    a.getDataHora().isAfter(MIN_DATAHORA);
            })            .filter(a -> {
                return a.getDataHora() == MAX_DATAHORA ||
                    a.getDataHora().isBefore(MAX_DATAHORA);
            })            
            .filter(a -> a.getCliente().getNome().equalsIgnoreCase(CLIENTE_NOME))
            .filter(a -> a.getCliente().getCpf().equalsIgnoreCase(CLIENTE_CPF))
            .count();
        
        var quantidadeEsperada = Long.valueOf(match);

        // act
        var result = agendamentoRepository.findAllByParams(
            PROCEDIMENTO_ID,
            PROCEDIMENTO_NOME,
            STATUS,
            MIN_DATAHORA,
            MAX_DATAHORA,
            CLIENTE_NOME,
            CLIENTE_ID,
            CLIENTE_CPF,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comProcedimentoId() {
        // arrange
        this.seedFindAllByParams();
        var PROCEDIMENTO_ID = 1L;

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getProcedimento().getId() == PROCEDIMENTO_ID);
        var quantidadeEsperada = Long.valueOf(correspondentes.count());

        // act
        var result = agendamentoRepository.findAllByParams(
            PROCEDIMENTO_ID,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comProcedimentoNome() {
        // arrange
        this.seedFindAllByParams();
        var PROCEDIMENTO_NOME = procedimento1.getNome();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> {
                return a.getProcedimento().getNome().equalsIgnoreCase(
                    PROCEDIMENTO_NOME);
            })
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            PROCEDIMENTO_NOME,
            null,
            null,
            null,
            null,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comStatus() {
        // arrange
        this.seedFindAllByParams();
        var STATUS = agendamento1.getStatus();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getStatus() == STATUS)
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            STATUS,
            null,
            null,
            null,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comMinDataHora() {
        // arrange
        this.seedFindAllByParams();
        var MIN_DATAHORA = agendamento1.getDataHora();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getDataHora().compareTo(MIN_DATAHORA) >= 0)
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            MIN_DATAHORA,
            null,
            null,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comMaxDataHora() {
        // arrange
        this.seedFindAllByParams();
        var MAX_DATAHORA = agendamento1.getDataHora();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getDataHora().compareTo(MAX_DATAHORA) <= 0)
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            null,
            MAX_DATAHORA,
            null,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comClienteNome() {
        // arrange
        this.seedFindAllByParams();
        var CLIENTE_NOME = cliente1.getNome();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getCliente().getNome().equalsIgnoreCase(CLIENTE_NOME))
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            null,
            null,
            CLIENTE_NOME,
            null,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comClienteId() {
        // arrange
        this.seedFindAllByParams();
        var CLIENTE_ID = cliente1.getId();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getCliente().getId() == CLIENTE_ID)
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            null,
            null,
            null,
            CLIENTE_ID,
            null,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }
    @Test
    void testFindAllByParams_comClienteCpf() {
        // arrange
        this.seedFindAllByParams();
        var CLIENTE_CPF = cliente1.getCpf();

        var correspondentes = agendamentoRepository.findAll().stream()
            .filter(a -> a.getCliente().getCpf().equalsIgnoreCase(CLIENTE_CPF))
            .count();
        var quantidadeEsperada = Long.valueOf(correspondentes);

        // act
        var result = agendamentoRepository.findAllByParams(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            CLIENTE_CPF,
            PAGEABLE
        );

        // assert
        assertEquals(quantidadeEsperada.intValue(), result.getContent().size());
    }

    void seedFindAllByParams() {
        // -- Seed Clientes -- 
        this.seedClientes();
        this.seedProcedimentos();
        this.seedAgendamentos();
    }

    private void seedAgendamentos() {
        this.agendamento1 = Agendamento.builder()
            .observacao("random observation")
            .status(StatusAgendamento.PENDENTE)
            .dataHora(LocalDateTime.now().plusDays(1).withNano(0))
            .procedimento(procedimento1)
            .cliente(cliente1)
            .build();

        this.agendamento2 = Agendamento.builder()
            .observacao("second observation")
            .status(StatusAgendamento.FINALIZADO)
            .dataHora(LocalDateTime.now().plusDays(100).withNano(0))
            .procedimento(procedimento2)
            .cliente(cliente2)
            .build();

        this.agendamento1 = agendamentoRepository.save(agendamento1);
        this.agendamento2 = agendamentoRepository.save(agendamento2);
    }

    private void seedProcedimentos() {
        // -- Seed Procedimentos -- 
        this.procedimento1 = Procedimento.builder()
            .nome("random name")
            .descricao("random description")
            .duracao(LocalTime.of(1, 0, 0))
            .valor(50.00)
            .build();

        this.procedimento2 = Procedimento.builder()
            .nome("second name")
            .descricao("second description")
            .duracao(LocalTime.of(2, 0, 0))
            .valor(100.00)
            .build();

        this.procedimento1 = procedimentoRepository.save(procedimento1);
        this.procedimento2 = procedimentoRepository.save(procedimento2);
    }

    private void seedClientes() {
        this.cliente1 = new ClienteBuilder()
            .nome("random name")
            .cpf("random cpf")
            .telefone("random number")
            .email("random email")
            .build();

        this.cliente2 = new ClienteBuilder()
            .nome("seconde name")
            .cpf("seconde cpf")
            .telefone("seconde number")
            .email("seconde email")
            .build();

        this.cliente1 = clienteRepository.save(cliente1);
        this.cliente2 = clienteRepository.save(cliente2);
    }
}