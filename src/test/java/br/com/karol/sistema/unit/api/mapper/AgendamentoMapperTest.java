package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
import br.com.karol.sistema.api.mapper.AgendamentoMapper;
import br.com.karol.sistema.builder.AgendamentoFactory;
import br.com.karol.sistema.domain.Agendamento;

@ExtendWith(MockitoExtension.class)
public class AgendamentoMapperTest {

    private static final Agendamento DEFAULT = AgendamentoFactory.getAgendamento();

    @InjectMocks
    private AgendamentoMapper mapper;


    @Test
    void testToDadosAgendamentoDTO() {
        // arrange
        Agendamento agendamento = DEFAULT;

        // act
        DadosAgendamentoDTO result = mapper.toDadosAgendamentoDTO(agendamento);

        // assert
        assertNotNull(result);
        assertEquals(agendamento.getId(), result.getId());
        assertEquals(agendamento.getStatus(), result.getStatus());
        assertEquals(agendamento.getObservacao(), result.getObservacao());
        assertEquals(agendamento.getDataHora(), result.getDataHora());
        assertNotNull(result.getProcedimento());
        assertNotNull(result.getCliente());
        assertEquals(agendamento.getDataCriacao(), result.getDataCriacao());
        assertEquals(agendamento.getDataModificacao(), result.getDataModificacao());
        assertEquals(agendamento.getUsuarioLogin(), result.getUsuarioLogin());
    }

    @Test
    void testToMeDadosAgendamentoDTO() {
        // arrange
        Agendamento agendamento = DEFAULT;

        // act
        MeDadosAgendamentoDTO result = mapper.toMeDadosAgendamentoDTO(agendamento);

        // assert
        assertNotNull(result);
        assertEquals(agendamento.getId(), result.getId());
        assertEquals(agendamento.getStatus(), result.getStatus());
        assertEquals(agendamento.getObservacao(), result.getObservacao());
        assertEquals(agendamento.getDataHora(), result.getDataHora());
        assertNotNull(result.getProcedimento());
    }

    @Test
    void testToObservacaoAtualizadaDTO() {
        // arrange
        Agendamento agendamento = DEFAULT;

        // act
        ObservacaoAtualizadaAgendamentoDTO result = mapper.toObservacaoAtualizadaAgendamentoDTO(agendamento);

        // assert
        assertEquals(agendamento.getId(), result.getId());
        assertEquals(agendamento.getObservacao(), result.getObservacao());
    }

    @Test
    void testToStatusAtualizadoAgendamentoDTO() {
        // arrange
        Agendamento agendamento = DEFAULT;

        // act
        StatusAtualizadoAgendamentoDTO result = mapper.toStatusAtualizadoAgendamentoDTO(agendamento);

        // assert
        assertNotNull(result);
        assertEquals(agendamento.getStatus(), result.getStatus());
    }

    @Test
    void testToPageDadosAgentamentoDTO() {
        // arrange
        Page<Agendamento> agendamento = new PageImpl<>(List.of(DEFAULT));

        // act
        Page<DadosAgendamentoDTO> result = mapper.toPageDadosAgentamentoDTO(agendamento);

        // assert
        assertNotNull(result);
        assertEquals(agendamento.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(agendamento.getContent().get(0).getStatus(), result.getContent().get(0).getStatus());
        assertEquals(agendamento.getContent().get(0).getObservacao(), result.getContent().get(0).getObservacao());
        assertEquals(agendamento.getContent().get(0).getDataHora(), result.getContent().get(0).getDataHora());
        assertNotNull(result.getContent().get(0).getProcedimento());
        assertNotNull(result.getContent().get(0).getCliente());
        assertEquals(agendamento.getContent().get(0).getDataCriacao(), result.getContent().get(0).getDataCriacao());
        assertEquals(agendamento.getContent().get(0).getDataModificacao(), result.getContent().get(0).getDataModificacao());
        assertEquals(agendamento.getContent().get(0).getUsuarioLogin(), result.getContent().get(0).getUsuarioLogin());
    }

    @Test
    void testToPageDadosBasicosAgentamentoDTO() {
        // arrange
        Page<Agendamento> agendamento = new PageImpl<>(List.of(DEFAULT));

        // act
        Page<DadosBasicosAgendamentoDTO> result = mapper.toPageDadosBasicosAgentamentoDTO(agendamento);

        // assert
        assertNotNull(result);
        assertEquals(agendamento.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(agendamento.getContent().get(0).getStatus(), result.getContent().get(0).getStatus());
        assertEquals(agendamento.getContent().get(0).getDataHora(), result.getContent().get(0).getDataHora());
        assertNotNull(result.getContent().get(0).getProcedimento());
        assertNotNull(result.getContent().get(0).getCliente());
    }
}