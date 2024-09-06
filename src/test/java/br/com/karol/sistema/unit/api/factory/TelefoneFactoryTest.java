package br.com.karol.sistema.unit.api.factory;

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

import br.com.karol.sistema.api.factory.TelefoneFactory;
import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.TelefoneValidator;
import br.com.karol.sistema.domain.valueobjects.Telefone;

@ExtendWith(MockitoExtension.class)
public class TelefoneFactoryTest {

    @Mock
    private List<TelefoneValidator> validators;
    @Mock
    private TelefoneValidator validador;
    @Mock
    private TelefoneFormatter formatter;

    @InjectMocks
    private TelefoneFactory mapper;

    @BeforeEach
    void setup() {
        this.validators = List.of(validador);
        this.mapper = new TelefoneFactory(validators, formatter);
    }


    @Test
    void testCreateTelefone() {
        // arrange
        String value = "47996525916";
        when(formatter.format(anyString())).thenReturn(value);

        // act
        Telefone result = mapper.createTelefone(value);

        // assert
        assertNotNull(result);
        assertEquals(value, result.getValue());
    }

    @Test
    void testToTelefoneOrNull_passandoNull() {
        // arrange
        String value = null;

        // act
        Telefone result = mapper.toTelefoneOrNull(value);

        // assert
        assertNull(result);
    }
    @Test
    void testToTelefoneOrNull_passandoBlank() {
        // arrange
        String value = "";

        // act
        Telefone result = mapper.toTelefoneOrNull(value);

        // assert
        assertNull(result);
    }
}