package br.com.karol.sistema.business.validators;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.validator.EmailValidator;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;

@Component
public class PatternEmailValidator implements EmailValidator {

    private static final String CLASSE = Email.class.getSimpleName();
 

    @Override
    public void validate(String value) {
        var validator = 
            org.apache.commons.validator.routines.EmailValidator.getInstance();
        
        if (!validator.isValid(value))
            throw new FieldValidationException(CLASSE);
    }
}