package br.com.karol.sistema.domain.valueobjects;

import java.util.List;

import br.com.karol.sistema.domain.validator.usuario.login.LoginValidator;
import br.com.karol.sistema.infra.exceptions.InvalidVOException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Login {

    private String value;

    public Login(String value, List<LoginValidator> validators) {
        if (value == null || value.isBlank()) throw new InvalidVOException(this.getClass(), "Não pode ser null/blank");

        if (validators == null || validators.size() == 0) throw new IllegalArgumentException("Deve fornecedor um ou mais validadores");
        validators.forEach(v -> v.validate(value));

        this.value = value;
    }
}