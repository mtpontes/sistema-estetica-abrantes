package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.TelefoneValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Embeddable
public class Telefone {

    private String value;

    public Telefone(String value, List<TelefoneValidator> validators, TelefoneFormatter formatter) {
        if (value.isBlank()) 
            throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        if (validators.size() == 0) 
            throw new RuntimeException("Deve fornecedor um ou mais validadores");

        this.value = formatter.format(value);
        validators.forEach(v -> v.validate(this.value));
    }
}