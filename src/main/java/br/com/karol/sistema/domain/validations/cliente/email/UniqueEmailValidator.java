package br.com.karol.sistema.domain.validations.cliente.email;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueEmailValidator implements EmailValidator {
    
    private static final Class<?> CLASSE = Email.class;
    private static final String EMAIL_ERROR_MESSAGE = "indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(String value) {

        if (repository.existsByEmailValue(value))
            throw new InvalidVOException(CLASSE, EMAIL_ERROR_MESSAGE);
    }
}