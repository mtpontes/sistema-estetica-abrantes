package br.com.karol.sistema.domain.valueobjects;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.usuario.senha.SenhaValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Senha {

    private String value;

    public Senha(String value, SenhaValidator validator, SenhaEncoder encoder) {
        if (value.isBlank()) 
            throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        validator.validate(value);

        this.value = encoder.encode(value);
    }
}