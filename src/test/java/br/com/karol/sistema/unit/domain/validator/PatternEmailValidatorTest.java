package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.cliente.email.PatternEmailValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternEmailValidatorTest {

    private PatternEmailValidator validator = new PatternEmailValidator();


    @Test
    void testEmailValido() {
        String emailValido = "usuario@example.com";
        assertDoesNotThrow(() -> validator.validate(emailValido));
    }

    @Test
    void testEmailNull() {
        assertThrows(FieldValidationException.class, () -> validator.validate(null));
    }

    @Test
    void testEmailVazio() {
        String emailVazio = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailVazio));
    }

    @Test
    void testEmailInvalido() {
        String emailInvalido = "usuario.example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailInvalido));
    }

    @Test
    void testEmailComEspacos() {
        String emailComEspacos = "usuario @example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailComEspacos));
    }

    @Test
    void testEmailSemUsuario() {
        String emailSemUsuario = "@example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailSemUsuario));
    }

    @Test
    void testEmailSemDominio() {
        String emailSemDominio = "usuario@";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailSemDominio));
    }
}