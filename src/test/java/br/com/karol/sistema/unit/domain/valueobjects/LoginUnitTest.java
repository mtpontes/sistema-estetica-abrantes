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

import br.com.karol.sistema.domain.validator.usuario.login.LoginValidator;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@ExtendWith(MockitoExtension.class)
public class LoginUnitTest {

    private final String VALUE = "login";

    @Mock
    private List<LoginValidator> validators;

    @Mock
    private LoginValidator validator;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
    }

    
    @Test
    void testDeveCriarLoginComValoresValidos() {
        assertDoesNotThrow(() -> new Login(VALUE, validators));
    }

    @Test
    void testNaoDeveCriarLoginQuandoValueForNull() {
        assertThrows(NullPointerException.class, () -> new Login(null, validators));
        assertThrows(FieldValidationException.class, () -> new Login("", validators));
    }
    
    @Test
    void testNaoDeveCriarLoginSemUmValidator() {
        assertThrows(NullPointerException.class, () -> new Login(VALUE, null));
        assertThrows(RuntimeException.class, () -> new Login(VALUE, Collections.emptyList()));
    }
}