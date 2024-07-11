package br.com.karol.sistema.domain.valueobjects;

import java.util.Objects;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.usuario.senha.SenhaValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Senha {

    private String value;

    public Senha(String value, SenhaValidator validator, SenhaEncoder encoder) {
        Objects.requireNonNull(validator, "Não pode ser null: validator");
        Objects.requireNonNull(encoder, "Não pode ser null: encoder");
        Objects.requireNonNull(value, "Não pode ser null: value");

        if (value.isBlank()) throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        validator.validate(value);

        this.value = encoder.encode(value);
    }
}