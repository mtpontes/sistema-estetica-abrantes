package br.com.karol.sistema.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Endereco {

    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;

    public Endereco(String rua, String numero, String cidade, String bairro, String estado) {
        this.notBlank(rua, "rua");
        this.rua = rua;

        this.notBlank(numero, "numero");
        this.numero = numero;

        this.notBlank(cidade, "cidade");
        this.cidade = cidade;

        this.notBlank(bairro, "bairro");
        this.bairro = bairro;

        this.notBlank(estado, "estado");
        this.estado = estado;
    }

    private void notBlank(String field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }
}