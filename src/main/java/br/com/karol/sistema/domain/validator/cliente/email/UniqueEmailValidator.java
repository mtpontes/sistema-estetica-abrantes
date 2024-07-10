package br.com.karol.sistema.domain.validator.cliente.email;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueEmailValidator implements EmailValidator {
    
    private static final String CLASSE = Email.class.getSimpleName();
    private static final String EMAIL_ERROR_MESSAGE = "indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(String value) {

        if (repository.existsByEmailValue(value))
            throw new FieldValidationException(CLASSE, EMAIL_ERROR_MESSAGE);
    }
}