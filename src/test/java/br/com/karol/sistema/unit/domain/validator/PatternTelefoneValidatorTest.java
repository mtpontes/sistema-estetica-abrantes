package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.cliente.telefone.PatternTelefoneValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternTelefoneValidatorTest {

    private PatternTelefoneValidator validator = new PatternTelefoneValidator();


    @Test
    void testTelefoneValido() {
        String telefoneValido = "+5511987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneValido));
    }

    @Test
    void testTelefoneVazio() {
        String telefoneVazio = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneVazio));
    }

    @Test
    void testTelefoneNull() {
        String telefoneVazio = null;
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneVazio));
    }

    @Test
    void testTelefoneInvalido() {
        String telefoneInvalido = "123";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneInvalido));
    }

    @Test
    void testTelefoneSemDDD() {
        String telefoneSemDDD = "987654321";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneSemDDD));    }

    @Test
    void testTelefoneComCodigoInternacional() {
        String telefoneComCodigoInternacional = "55 11 987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneComCodigoInternacional));
    }

    @Test
    void testTelefoneComParenteses() {
        String telefoneComParenteses = "55 (11) 987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneComParenteses));
    }
    @Test
    void testTelefoneComHifen() {
        String telefoneComHifen = "55 (11) 98765-4321";
        assertDoesNotThrow(() -> validator.validate(telefoneComHifen));
    }

    @Test
    void testTelefoneComSinalDeMais() {
        String telefoneComSinalDeMais = "+55 (11) 98765-4321";
        assertDoesNotThrow(() -> validator.validate(telefoneComSinalDeMais));
    }

    @Test
    void testTelefoneComCaracteresEspeciais() {
        String telefoneComCaracteresEspeciais = "+55 (11) &* 98765-4321";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneComCaracteresEspeciais));
    }

    @Test
    void testTelefoneComLetras() {
        String telefoneComLetras = "+5511abc987654";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneComLetras));
    }
}