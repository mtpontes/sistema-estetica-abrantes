package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.api.mapper.ProcedimentoMapper;
import br.com.karol.sistema.builder.ProcedimentoFactory;
import br.com.karol.sistema.domain.Procedimento;

public class ProcedimentoMapperTest {

    private static final Procedimento DEFAULT = ProcedimentoFactory.getProcedimento();

    private final ProcedimentoMapper mapper = new ProcedimentoMapper();


    @Test
    void testToProcedimento() {
        // arrange
        CriarProcedimentoDTO dto = new CriarProcedimentoDTO("nome", "descricao", LocalTime.now().plusHours(1), 59.00);

        // act
        Procedimento result = mapper.toProcedimento(dto);

        // assert
        assertNotNull(result);
        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getDescricao(), result.getDescricao());
        assertEquals(dto.getDuracao(), result.getDuracao());
        assertEquals(dto.getValor(), result.getValor());
    }

    @Test
    void testToDadosProcedimentoDTO() {
        // arrange
        Procedimento procedimento = DEFAULT;

        // act
        DadosProcedimentoDTO dto = mapper.toDadosProcedimentoDTO(procedimento);

        // assert
        assertNotNull(dto);
        assertEquals(procedimento.getNome(), dto.getNome());
        assertEquals(procedimento.getDescricao(), dto.getDescricao());
        assertEquals(procedimento.getDuracao(), dto.getDuracao());
        assertEquals(procedimento.getValor(), dto.getValor());
    }

    @Test
    void toPageDadosProcedimento() {
        // arrange
        Page<Procedimento> entry = new PageImpl<>(List.of(DEFAULT));

        // act
        Page<DadosProcedimentoDTO> result = mapper.toPageDadosProcedimentoDTO(entry);

        // assert
        assertNotNull(result);
        assertEquals(entry.getContent().get(0).getNome(), entry.getContent().get(0).getNome());
        assertEquals(entry.getContent().get(0).getDescricao(), entry.getContent().get(0).getDescricao());
        assertEquals(entry.getContent().get(0).getDuracao(), entry.getContent().get(0).getDuracao());
        assertEquals(entry.getContent().get(0).getValor(), entry.getContent().get(0).getValor());
    }
}