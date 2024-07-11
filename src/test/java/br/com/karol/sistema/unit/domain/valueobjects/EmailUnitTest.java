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

import br.com.karol.sistema.domain.validator.cliente.email.EmailValidator;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@ExtendWith(MockitoExtension.class)
public class EmailUnitTest {

    private final String VALUE = "email";

    @Mock
    private List<EmailValidator> validators;
    @Mock
    private EmailValidator validator;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
    }

    
    @Test
    void testDeveCriarEmailComValoresValidos() {
        assertDoesNotThrow(() -> new Email(VALUE, validators));
    }

    @Test
    void testNaoDeveCriarEmailQuandoValueForNull() {
        assertThrows(NullPointerException.class, () -> new Email(null, validators));
        assertThrows(FieldValidationException.class, () -> new Email("", validators));
    }
    
    @Test
    void testNaoDeveCriarEmailSemUmValidator() {
        assertThrows(NullPointerException.class, () -> new Email(VALUE, null));
        assertThrows(RuntimeException.class, () -> new Email(VALUE, Collections.emptyList()));
    }
}