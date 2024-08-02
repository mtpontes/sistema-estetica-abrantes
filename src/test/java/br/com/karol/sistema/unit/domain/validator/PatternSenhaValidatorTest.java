package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.karol.sistema.business.validators.PatternSenhaValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

public class PatternSenhaValidatorTest {

    private PatternSenhaValidator validator = new PatternSenhaValidator();


    @Test
    void testSenha_valida() {
        String senhaValida = "Senha1@valida";
        assertDoesNotThrow(() -> validator.validate(senhaValida));
    }

    @Test
    void testSenha_null() {
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }

    @Test
    void testSenha_vazia() {
        String senhaVazia = "";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaVazia));
    }
    
    @Test
    void testSenha_curta() {
        String senhaCurta = "Curta1@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaCurta));
    }

    @Test
    void testSenha_longaDemais() {
        String senhaLongaDemais = "SenhaMuitoMuitoLonga1@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaLongaDemais));
    }

    @Test
    void testSenha_comEspaco() {
        String senhaComEspaco = "Senha 1@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComEspaco));
    }

    @Test
    void testSenha_semMaiuscula() {
        String senhaSemMaiuscula = "senha1@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemMaiuscula));
    }

    @Test
    void testSenha_semMinuscula() {
        String senhaSemMinuscula = "SENHA1@VALIDA";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemMinuscula));
    }

    @Test
    void testSenha_semDigito() {
        String senhaSemDigito = "Senha@Valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemDigito));
    }

    @Test
    void testSenha_semEspecial() {
        String senhaSemEspecial = "Senha1Valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaSemEspecial));
    }

    @Test
    void testSenha_comCaracteresRepetidos() {
        String senhaComCaracteresRepetidos = "Senha1@@@valida";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComCaracteresRepetidos));
    }

    @Test
    void testSenha_comSequenciaIlegal() {
        String senhaComSequenciaIlegal = "Abc1234@";
        assertThrows(FieldValidationException.class, () -> validator.validate(senhaComSequenciaIlegal));
    }
}