package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.business.validators.PatternLoginValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternLoginValidatorTest {

    private PatternLoginValidator validator = new PatternLoginValidator();


    @Test
    void testLogin_valido() {
        assertDoesNotThrow(() -> validator.validate("login"));
    }
    
    @Test
    void testLogin_vazio() {
        assertThrows(FieldValidationException.class, () -> validator.validate(""));
    }

    @Test
    void testLogin_nulo() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }

    @Test
    void testLogin_primeiraLetraMaiuscula() {
        assertThrows(FieldValidationException.class, () -> validator.validate("Login"));
    }

    @Test
    void testLogin_curto() {
        assertThrows(FieldValidationException.class, () -> validator.validate("lo"));
    }

    @Test
    void testLogin_longo() {
        assertThrows(FieldValidationException.class, 
            () -> validator.validate("llooggiinnLLoonnggG21"));
    }

    @Test
    void testLogin_comEspaco() {
        assertThrows(FieldValidationException.class, () -> validator.validate("login com espadaco"));
    }

    @Test
    void testLogin_latino() {
        assertThrows(FieldValidationException.class, () -> validator.validate("espaço"));
    }

    @Test
    void testLogin_comCaracteresEspeciais() {
        assertThrows(FieldValidationException.class, () -> validator.validate("log@in"));
    }
}