package br.com.karol.sistema.business.validators;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueCpfValidator implements CpfValidator {
    
    private static final String CLASSE = CPF.class.getSimpleName();
    private static final String CPF_ERROR_MESSAGE = "indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(String value) {
        if (repository.existsByCpfValue(value))
            throw new FieldValidationException(CLASSE, CPF_ERROR_MESSAGE);
    }
}