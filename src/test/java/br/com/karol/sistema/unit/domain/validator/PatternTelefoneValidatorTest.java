package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.cliente.telefone.PatternTelefoneValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternTelefoneValidatorTest {

    private PatternTelefoneValidator validator = new PatternTelefoneValidator();


    @Test
    void testTelefone_valido() {
        String telefoneValido = "+5511987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneValido));
    }

    @Test
    void testTelefone_vazio() {
        String telefoneVazio = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneVazio));
    }

    @Test
    void testTelefone_null() {
        String telefoneVazio = null;
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneVazio));
    }

    @Test
    void testTelefone_invalido() {
        String telefoneInvalido = "123";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneInvalido));
    }

    @Test
    void testTelefone_semDDD() {
        String telefoneSemDDD = "987654321";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneSemDDD));    }

    @Test
    void testTelefone_comCodigoInternacional() {
        String telefoneComCodigoInternacional = "55 11 987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneComCodigoInternacional));
    }

    @Test
    void testTelefone_comParenteses() {
        String telefoneComParenteses = "55 (11) 987654321";
        assertDoesNotThrow(() -> validator.validate(telefoneComParenteses));
    }
    @Test
    void testTelefone_comHifen() {
        String telefoneComHifen = "55 (11) 98765-4321";
        assertDoesNotThrow(() -> validator.validate(telefoneComHifen));
    }

    @Test
    void testTelefone_comSinalDeMais() {
        String telefoneComSinalDeMais = "+55 (11) 98765-4321";
        assertDoesNotThrow(() -> validator.validate(telefoneComSinalDeMais));
    }

    @Test
    void testTelefone_comCaracteresEspeciais() {
        String telefoneComCaracteresEspeciais = "+55 (11) &* 98765-4321";
        assertThrows(FieldValidationException.class, 
            () -> validator.validate(telefoneComCaracteresEspeciais));
    }

    @Test
    void testTelefone_comLetras() {
        String telefoneComLetras = "+5511abc987654";
        assertThrows(FieldValidationException.class, () -> validator.validate(telefoneComLetras));
    }
}