package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validations.usuario.senha.SenhaValidator;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Senha {

    private String value;

    public Senha(String value, List<SenhaValidator> validators, SenhaEncoder encoder) {
        if (value == null || value.isBlank()) throw new InvalidVOException(this.getClass(), "Não pode ser null/blank");
        
        if (validators == null || validators.size() == 0) throw new IllegalArgumentException("Deve fornecedor um ou mais validadores");
        if (encoder == null) throw new IllegalArgumentException("Deve fornecedor um encoder");
        validators.forEach(v -> v.validate(value));

        this.value = encoder.encode(value);
    }
}