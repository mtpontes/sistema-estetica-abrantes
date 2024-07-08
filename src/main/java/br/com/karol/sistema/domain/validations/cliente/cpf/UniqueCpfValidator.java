package br.com.karol.sistema.domain.validations.cliente.cpf;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueCpfValidator implements CpfValidator {
    
    private static final Class<?> CLASSE = CPF.class;
    private static final String CPF_ERROR_MESSAGE = "indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(String value) {
        if (repository.existsByCpf(value))
            throw new InvalidVOException(CLASSE, CPF_ERROR_MESSAGE);
    }
}