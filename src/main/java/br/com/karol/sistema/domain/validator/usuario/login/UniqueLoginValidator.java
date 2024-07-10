package br.com.karol.sistema.domain.validator.usuario.login;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueLoginValidator implements LoginValidator {
    
    private static final String CLASSE = Login.class.getSimpleName();
    private static final String CPF_ERROR_MESSAGE = "indisponível";

    private UsuarioRepository repository;


    @Override
    public void validate(String login) {
        if (repository.existsByLoginValue(login))
            throw new FieldValidationException(CLASSE, CPF_ERROR_MESSAGE);
    }
}