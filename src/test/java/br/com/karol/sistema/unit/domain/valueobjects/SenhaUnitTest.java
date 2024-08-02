package br.com.karol.sistema.unit.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@ExtendWith(MockitoExtension.class)
public class SenhaUnitTest {

    private final String VALUE = "senha";

    @Mock
    private SenhaValidator validator;
    @Mock
    private SenhaEncoder encoder;

    
    @Test
    void testDeveCriarSenhaComValoresValidos() {
        assertDoesNotThrow(() -> new Senha(VALUE, validator, encoder));
    }

    @Test
    void testNaoDeveCriarSenhaQuandoValueForNull() {
        assertThrows(NullPointerException.class, () -> new Senha(null, validator, encoder));
        assertThrows(FieldValidationException.class, () -> new Senha("", validator, encoder));
    }
    
    @Test
    void testNaoDeveCriarSenhaSemUmValidatorEEncoder() {
        assertThrows(NullPointerException.class, () -> new Senha(VALUE, null, encoder));
        assertThrows(NullPointerException.class, () -> new Senha(VALUE, validator, null));
    }
}