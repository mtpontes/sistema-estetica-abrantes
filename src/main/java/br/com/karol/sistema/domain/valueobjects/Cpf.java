package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.business.formatters.CpfFormatter;
import br.com.karol.sistema.domain.validator.CpfValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Cpf {

    private String value;

    public Cpf(String value, List<CpfValidator> validators, CpfFormatter formatter) {
        if (value.isBlank()) 
            throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser null/blank");
        if (validators.size() == 0) 
            throw new RuntimeException("Deve fornecedor um ou mais validadores");
            
        this.value = formatter.format(value);
        validators.forEach(v -> v.validate(this.value));
    }
}