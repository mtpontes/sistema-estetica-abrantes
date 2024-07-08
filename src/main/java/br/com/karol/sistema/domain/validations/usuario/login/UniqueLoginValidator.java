package br.com.karol.sistema.domain.validations.usuario.login;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueLoginValidator implements LoginValidator {
    
    private static final Class<?> CLASSE = Login.class;
    private static final String CPF_ERROR_MESSAGE = "indisponível";

    private UsuarioRepository repository;


    @Override
    public void validate(String login) {
        if (repository.existsByLoginValue(login))
            throw new InvalidVOException(CLASSE, CPF_ERROR_MESSAGE);
    }
}