package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.cliente.cpf.PatternCpfValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternCpfValidatorTest {

    private PatternCpfValidator validator = new PatternCpfValidator();

    
    @Test
    void testCpfValido() {
        String cpfValido = "123.456.789-09";
        assertDoesNotThrow(() -> validator.validate(cpfValido));
    }

    @Test
    void testCpfVazio() {
        String cpfVazio = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(cpfVazio));
    }

    @Test
    void testCpfInvalido() {
        String cpfInvalido = "123.456.789-00"; // CPF inválido
        assertThrows(FieldValidationException.class, () -> validator.validate(cpfInvalido));
    }

    @Test
    void testCpfComLetras() {
        String cpfComLetras = "123.ABC.789-09"; // CPF com letras
        assertThrows(FieldValidationException.class, () -> validator.validate(cpfComLetras));
    }

    @Test
    void testCpfComCaracteresEspeciais() {
        String cpfComCaracteresEspeciais = "123$456@789-09"; // CPF com caracteres especiais
        assertThrows(FieldValidationException.class, () -> validator.validate(cpfComCaracteresEspeciais));
    }
}