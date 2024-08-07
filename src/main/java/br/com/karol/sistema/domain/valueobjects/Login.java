package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.validator.LoginValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class Login {

    private String value;

    public Login(String value, List<LoginValidator> validators) {
        if (value.isBlank()) 
            throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        if (validators.size() == 0) 
            throw new RuntimeException("Deve fornecedor um ou mais validadores");
        validators.forEach(v -> v.validate(value));

        this.value = value;
    }
}