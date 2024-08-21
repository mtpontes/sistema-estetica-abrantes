package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.mapper.CpfMapper;
import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.domain.valueobjects.Cpf;

@ExtendWith(MockitoExtension.class)
public class CpfMapperTest {

    @Mock
    private List<CpfValidator> validators;
    @Mock
    private CpfValidator validador;
    @Mock
    private CpfFormatter formatter;

    @InjectMocks
    private CpfMapper mapper;

    @BeforeEach
    void setup() {
        this.validators = List.of(validador);
        this.mapper = new CpfMapper(validators, formatter);
    }


    @Test
    void tesToToCpf() {
        // arrange
        String value = "47996525916";
        when(formatter.format(anyString())).thenReturn(value);

        // act
        Cpf result = mapper.toCpf(value);

        // assert
        assertNotNull(result);
        assertEquals(value, result.getValue());
    }

    @Test
    void testToCpfOrNullPassandoNull() {
        // arrange
        String value = null;

        // act
        Cpf result = mapper.toCpfOrNull(value);

        // assert
        assertNull(result);
    }
    @Test
    void testToCpfOrNullPassandoBlank() {
        // arrange
        String value = "";

        // act
        Cpf result = mapper.toCpfOrNull(value);

        // assert
        assertNull(result);
    }
}