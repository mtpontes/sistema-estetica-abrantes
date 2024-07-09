package br.com.karol.sistema.domain.validator.usuario.senha;

import java.util.List;
import java.util.Properties;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RepeatCharacterRegexRule;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;

@Component
public class PatternSenhaValidator implements SenhaValidator {

    private static final Class<?> CLASSE = Senha.class;


    @Override
    public void validate(String senha) {
        PasswordData password = new PasswordData(senha);
        MessageResolver personalizedMessageErrors = this.getMessageErrors();
        
        PasswordValidator validator = new PasswordValidator(personalizedMessageErrors, List.of(
            new LengthRule(8, 20),
            new WhitespaceRule(),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(EnglishCharacterData.Special, 1),
            new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
            new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
            new IllegalSequenceRule(EnglishSequenceData.USQwerty),
            new RepeatCharacterRegexRule(3)
        ));

        var result = validator.validate(password);
        if (!result.isValid()) {
            throw new InvalidVOException(CLASSE, validator.getMessages(result).toString().replace("[", "").replace("]", ""));
        }
    }

    private MessageResolver getMessageErrors() {
        Properties props = new Properties();
        props.setProperty("TOO_SHORT", "Deve conter no mínimo 8 caracteres");
        props.setProperty("TOO_LONG", "Deve conter no máximo 8 caracteres");
        props.setProperty("ILLEGAL_WHITESPACE", "Não deve conter espaços");
        props.setProperty("INSUFFICIENT_UPPERCASE", "Deve conter um caractere maiúsculo");
        props.setProperty("INSUFFICIENT_LOWERCASE", "Deve conter um caractere minúsculo");
        props.setProperty("INSUFFICIENT_DIGIT", "Deve conter um caractere numérico");
        props.setProperty("INSUFFICIENT_SPECIAL", "Deve conter um caractere especial");
        props.setProperty("ILLEGAL_MATCH", "Não deve conter caracteres sequencialmente repetidos, previsíveis ou incomuns");
        props.setProperty("ILLEGAL_QWERTY_SEQUENCE", "Sequência de caracteres não permitida");
        props.setProperty("ILLEGAL_NUMERICAL_SEQUENCE", "Sequência numérica não permitida");
        return new PropertiesMessageResolver(props);
    }
}