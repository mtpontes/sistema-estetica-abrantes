package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.validator.cliente.email.EmailValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Email {

    private String value;

    public Email(String value, List<EmailValidator> validator) {
        if (value == null || value.isEmpty()) throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser null/blank");

        if (validator == null) throw new RuntimeException("Deve fornecer um validador");
        validator.forEach(v -> v.validate(value));

        this.value = value;
    }
}