package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

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
        if (value == null || value.isBlank()) throw new FieldValidationException(this.getClass().getSimpleName(), "Não pode ser null/blank");
        
        if (validators == null || validators.size() == 0) throw new RuntimeException("Deve fornecedor um ou mais validadores");
        if (formatter == null) throw new RuntimeException("Deve fornecedor um formatador");

        this.value = formatter.format(value);
    }
}