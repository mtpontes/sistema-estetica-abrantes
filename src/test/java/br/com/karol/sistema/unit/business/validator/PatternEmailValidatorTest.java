package br.com.karol.sistema.unit.business.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.business.validators.PatternEmailValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternEmailValidatorTest {

    private PatternEmailValidator validator = new PatternEmailValidator();


    @Test
    void testEmail_valido() {
        String emailValido = "usuario@example.com";
        assertDoesNotThrow(() -> validator.validate(emailValido));
    }

    @Test
    void testEmail_null() {
        assertThrows(FieldValidationException.class, () -> validator.validate(null));
    }

    @Test
    void testEmail_vazio() {
        String emailVazio = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailVazio));
    }

    @Test
    void testEmail_invalido() {
        String emailInvalido = "usuario.example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailInvalido));
    }

    @Test
    void testEmail_comEspacos() {
        String emailComEspacos = "usuario @example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailComEspacos));
    }

    @Test
    void testEmail_semUsuario() {
        String emailSemUsuario = "@example.com";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailSemUsuario));
    }

    @Test
    void testEmail_semDominio() {
        String emailSemDominio = "usuario@";
        assertThrows(FieldValidationException.class, () -> validator.validate(emailSemDominio));
    }
}