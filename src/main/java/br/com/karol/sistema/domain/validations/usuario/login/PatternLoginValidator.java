package br.com.karol.sistema.domain.validations.usuario.login;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatternLoginValidator implements LoginValidator {

    private static final Class<?> CLASSE = Login.class;

    // Primeira letra deve ser maiuscula
    private static final String VALIDA_PRIMEIRO_CARACTERE = "^[A-Z].*";

    // Deve ter um comprimento entre 3 e 20 caracteres
    private static final Integer TAMANHO_MINIMO = 3;
    private static final Integer TAMANHO_MAXIMO = 20;

    // Caracteres permitidos
    private static final String VALIDA_CARACTERES = "^[a-zA-Z0-9._-]+$";

    
    @Override
    public void validate(String login) {
        this.validatePrimeiroCaractere(login);
        this.validateTamanhoDaString(login);
        this.validateCaracteresPermitidos(login);
    }

    private void validatePrimeiroCaractere(String login) {
        if (login.matches(VALIDA_PRIMEIRO_CARACTERE))
            throw new InvalidVOException(CLASSE, "Primeiro caractere deve ser uma letra minúscula");
    }

    private void validateTamanhoDaString(String login) {
        if (login.length() < TAMANHO_MINIMO && login.length() > TAMANHO_MAXIMO)
            throw new InvalidVOException(CLASSE, String.format("Login deve ter entre %d a  d% caracteres", TAMANHO_MINIMO, TAMANHO_MAXIMO));
    }
    
    private void validateCaracteresPermitidos(String login) {
        if (!login.matches(VALIDA_CARACTERES)) {
            throw new InvalidVOException(CLASSE, "Login deve conter apenas letras, números, pontos, hífens e subtraços");
        }
    }
}