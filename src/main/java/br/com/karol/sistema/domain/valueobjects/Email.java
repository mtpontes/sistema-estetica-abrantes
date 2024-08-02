package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.validator.EmailValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class Email {

    private String value;

    public Email(String value, List<EmailValidator> validators) {
        if (value.isBlank()) 
            throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        if (validators.size() == 0) 
            throw new RuntimeException("Deve fornecer um validador");
        validators.forEach(v -> v.validate(value));

        this.value = value;
    }
}