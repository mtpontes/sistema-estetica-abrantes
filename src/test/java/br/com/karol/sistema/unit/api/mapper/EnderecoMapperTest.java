package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.api.mapper.EnderecoMapper;
import br.com.karol.sistema.domain.Endereco;

public class EnderecoMapperTest {

    private final EnderecoMapper mapper = new EnderecoMapper();


    @Test
    void testToEndereco() {
        // arrange
        EnderecoDTO dto = new EnderecoDTO("rua", "numero", "cidade", "bairro", "estado");

        // act
        Endereco result = mapper.toEndereco(dto);

        // assert
        assertNotNull(result);
        assertEquals(dto.getRua(), result.getRua());
        assertEquals(dto.getNumero(), result.getNumero());
        assertEquals(dto.getCidade(), result.getCidade());
        assertEquals(dto.getBairro(), result.getBairro());
        assertEquals(dto.getEstado(), result.getEstado());
    }

    @Test
    void testToEnderecoDTO() {
        // arrange
        Endereco entry = new Endereco("rua", "numero", "cidade", "bairro", "estado");

        // act
        EnderecoDTO result = mapper.toEnderecoDTO(entry);

        // assert
        assertNotNull(result);
        assertEquals(entry.getRua(), result.getRua());
        assertEquals(entry.getNumero(), result.getNumero());
        assertEquals(entry.getCidade(), result.getCidade());
        assertEquals(entry.getBairro(), result.getBairro());
        assertEquals(entry.getEstado(), result.getEstado());
    }
}