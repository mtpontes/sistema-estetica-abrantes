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

import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.validator.cliente.cpf.CpfValidator;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@ExtendWith(MockitoExtension.class)
public class CpfUnitTest {

    private final String VALUE = "cpf";

    @Mock
    private List<CpfValidator> validators;
    @Mock
    private CpfValidator validator;
    @Mock
    private CpfFormatter formatter;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
    }

    
    @Test
    void testCriarCpfComValoresValidos() {
        assertDoesNotThrow(() -> new Cpf(VALUE, validators, formatter));
    }

    @Test
    void testNaoDeveCriarCpfQuandoValueForNull() {
        assertThrows(NullPointerException.class, () -> new Cpf(null, validators, formatter));
        assertThrows(FieldValidationException.class, () -> new Cpf("", validators, formatter));
    }
    
    @Test
    void testNaoDeveCriarCpfSemUmValidator() {
        assertThrows(NullPointerException.class, () -> new Cpf(VALUE, null, formatter));
        assertThrows(RuntimeException.class, () -> new Cpf(VALUE, Collections.emptyList(), formatter));
    }

    @Test
    void testNaoDeveCriarCpfSemUmFormatter() {
        assertThrows(NullPointerException.class, () -> new Cpf(VALUE, validators, null));
    }
}