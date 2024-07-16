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

import br.com.karol.sistema.api.mapper.TelefoneMapper;
import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.cliente.telefone.TelefoneValidator;
import br.com.karol.sistema.domain.valueobjects.Telefone;

@ExtendWith(MockitoExtension.class)
public class TelefoneMapperTest {

    @Mock
    private List<TelefoneValidator> validators;
    @Mock
    private TelefoneValidator validador;
    @Mock
    private TelefoneFormatter formatter;

    @InjectMocks
    private TelefoneMapper mapper;

    @BeforeEach
    void setup() {
        this.validators = List.of(validador);
        this.mapper = new TelefoneMapper(validators, formatter);
    }


    @Test
    void testToTelefone() {
        // arrange
        String value = "47996525916";
        when(formatter.format(anyString())).thenReturn(value);

        // act
        Telefone result = mapper.toTelefone(value);

        // assert
        assertNotNull(result);
        assertEquals(value, result.getValue());
    }

    @Test
    void testToTelefoneOrNullPassandoNull() {
        // arrange
        String value = null;

        // act
        Telefone result = mapper.toTelefoneOrNull(value);

        // assert
        assertNull(result);
    }
    @Test
    void testToTelefoneOrNullPassandoBlank() {
        // arrange
        String value = "";

        // act
        Telefone result = mapper.toTelefoneOrNull(value);

        // assert
        assertNull(result);
    }
}