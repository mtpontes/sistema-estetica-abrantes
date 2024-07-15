package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.usuario.login.PatternLoginValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternLoginValidatorTest {

    private PatternLoginValidator validator = new PatternLoginValidator();


    @Test
    void testLoginValido() {
        assertDoesNotThrow(() -> validator.validate("login"));
    }
    
    @Test
    void testLoginVazio() {
        assertThrows(FieldValidationException.class, () -> validator.validate(""));
    }

    @Test
    void testLoginNulo() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }

    @Test
    void testLoginPrimeiraLetraMaiuscula() {
        assertThrows(FieldValidationException.class, () -> validator.validate("Login"));
    }

    @Test
    void testLoginCurto() {
        assertThrows(FieldValidationException.class, () -> validator.validate("lo"));
    }

    @Test
    void testLoginLongo() {
        assertThrows(FieldValidationException.class, 
            () -> validator.validate("llooggiinnLLoonnggG21"));
    }

    @Test
    void testLoginComEspaco() {
        assertThrows(FieldValidationException.class, () -> validator.validate("login com espadaco"));
    }

    @Test
    void testLoginLatino() {
        assertThrows(FieldValidationException.class, () -> validator.validate("espaço"));
    }

    @Test
    void testLoginComCaracteresEspeciais() {
        assertThrows(FieldValidationException.class, () -> validator.validate("log@in"));
    }
}