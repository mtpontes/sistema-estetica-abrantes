package br.com.karol.sistema.domain.validator.cliente.telefone;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueTelefoneValidator implements TelefoneValidator {
    
    private static final String TELEFONE_ERROR_MESSAGE = "indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(String value) {
        if (repository.existsByTelefoneValue(value))
            throw new IllegalArgumentException(TELEFONE_ERROR_MESSAGE);
    }
}