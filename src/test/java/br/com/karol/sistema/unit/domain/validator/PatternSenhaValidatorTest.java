package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.domain.validator.usuario.senha.PatternSenhaValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternSenhaValidatorTest {

    private PatternSenhaValidator validator = new PatternSenhaValidator();


    @Test
    void testSenhaValida() {
        String senhaValida = "Senha1@valida";
        assertDoesNotThrow(() -> validator.validate(senhaValida));
    }

    @Test
    void testSenhaNull() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }

    @Test
    void testSenhaVazia() {
        String senhaVazia = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaVazia));
    }
    
    @Test
    void testSenhaCurta() {
        String senhaCurta = "Curta1@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaCurta));
    }

    @Test
    void testSenhaLongaDemais() {
        String senhaLongaDemais = "SenhaMuitoMuitoLonga1@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaLongaDemais));
    }

    @Test
    void testSenhaComEspaco() {
        String senhaComEspaco = "Senha 1@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComEspaco));
    }

    @Test
    void testSenhaSemMaiuscula() {
        String senhaSemMaiuscula = "senha1@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemMaiuscula));
    }

    @Test
    void testSenhaSemMinuscula() {
        String senhaSemMinuscula = "SENHA1@VALIDA";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemMinuscula));
    }

    @Test
    void testSenhaSemDigito() {
        String senhaSemDigito = "Senha@Valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemDigito));
    }

    @Test
    void testSenhaSemEspecial() {
        String senhaSemEspecial = "Senha1Valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemEspecial));
    }

    @Test
    void testSenhaComCaracteresRepetidos() {
        String senhaComCaracteresRepetidos = "Senha1@@@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComCaracteresRepetidos));
    }

    @Test
    void testSenhaComSequenciaIlegal() {
        String senhaComSequenciaIlegal = "Abc1234@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComSequenciaIlegal));
    }
}