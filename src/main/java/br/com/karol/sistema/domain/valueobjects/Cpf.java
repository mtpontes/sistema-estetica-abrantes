package br.com.karol.sistema.domain.valueobjects;

import java.util.List;
import java.util.Objects;

import br.com.karol.sistema.domain.formatter.CpfFormatter;
import br.com.karol.sistema.domain.validator.cliente.cpf.CpfValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Cpf {

    private String value;

    public Cpf(String value, List<CpfValidator> validators, CpfFormatter formatter) {
        String notNullMessage = "Não pode ser null: ";
        Objects.requireNonNull(value, notNullMessage + "value");
        Objects.requireNonNull(validators, notNullMessage + "validators");
        Objects.requireNonNull(formatter, notNullMessage + "formatter");

        if (value.isBlank()) throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser null/blank");
        if (validators.size() == 0) throw new IllegalStateException("Deve fornecedor um ou mais validadores");
        validators.forEach(v -> v.validate(value));

        this.value = formatter.format(value);
    }
}