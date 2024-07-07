package br.com.karol.sistema.domain.validations.cliente;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UniqueFieldsValidator implements ClienteValidator {
    
    private static final String CPF_ERROR_MESSAGE = "CPF indisponível";
    private static final String TELEFONE_ERROR_MESSAGE = "Telefone indisponível";
    private static final String EMAIL_ERROR_MESSAGE = "Email indisponível";

    private ClienteRepository repository;


    @Override
    public void validate(Cliente dados) {
        if (repository.existsByCpf(dados.getCpf()))
            throw new IllegalArgumentException(CPF_ERROR_MESSAGE);

        if (repository.existsByTelefone(dados.getTelefone()))
            throw new IllegalArgumentException(TELEFONE_ERROR_MESSAGE);

        if (repository.existsByEmail(dados.getEmail()))
            throw new IllegalArgumentException(EMAIL_ERROR_MESSAGE);
    }
}