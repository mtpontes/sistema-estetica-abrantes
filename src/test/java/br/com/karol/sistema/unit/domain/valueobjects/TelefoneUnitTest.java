package br.com.karol.sistema.unit.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.TelefoneValidator;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@ExtendWith(MockitoExtension.class)
public class TelefoneUnitTest {

    private final String VALUE = "telefone";

    @Mock
    private List<TelefoneValidator> validators;
    @Mock
    private TelefoneValidator validator;
    @Mock
    private TelefoneFormatter formatter;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
    }

    
    @Test
    void testDeveCriarTelefoneComValoresValidos() {
        assertDoesNotThrow(() -> new Telefone(VALUE, validators, formatter));
    }

    @Test
    void testNaoDeveCriarTelefoneQuandoValueForNull() {
        assertThrows(NullPointerException.class, 
            () -> new Telefone(null, validators, formatter));
        assertThrows(FieldValidationException.class, 
            () -> new Telefone("", validators, formatter));
    }
    
    @Test
    void testNaoDeveCriarTelefoneSemUmValidator() {
        assertThrows(NullPointerException.class, 
            () -> new Telefone(VALUE, null, formatter));
        assertThrows(RuntimeException.class, 
            () -> new Telefone(VALUE, Collections.emptyList(), formatter));
    }

    @Test
    void testNaoDeveCriarTelefoneSemUmFormatter() {
        assertThrows(NullPointerException.class, 
            () -> new Telefone(VALUE, validators, null));
    }
}