package br.com.karol.sistema.domain.validator.cliente.email;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;

@Component
public class PatternEmailValidator implements EmailValidator {

    private static final Class<?> CLASSE = Email.class;
 

    @Override
    public void validate(String value) {
        var validator = org.apache.commons.validator.routines.EmailValidator.getInstance();
        
        if (!validator.isValid(value))
            throw new InvalidVOException(CLASSE);
    }
}