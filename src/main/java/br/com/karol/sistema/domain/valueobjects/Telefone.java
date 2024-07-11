package br.com.karol.sistema.domain.valueobjects;

import java.util.List;
import java.util.Objects;

import br.com.karol.sistema.domain.formatter.TelefoneFormatter;
import br.com.karol.sistema.domain.validator.cliente.telefone.TelefoneValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Telefone {

    private String value;

    public Telefone(String value, List<TelefoneValidator> validators, TelefoneFormatter formatter) {
        String notNullMessage = "Não pode ser null: ";
        Objects.requireNonNull(value, notNullMessage + "value");
        Objects.requireNonNull(validators, notNullMessage + "validators");
        Objects.requireNonNull(formatter, notNullMessage + "formatter");

        if (value.isBlank()) throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser blank");
        if (validators.size() == 0) throw new RuntimeException("Deve fornecedor um ou mais validadores");

        this.value = formatter.format(value);
    }
}