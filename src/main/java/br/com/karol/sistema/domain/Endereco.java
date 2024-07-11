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
        this.rua = this.notBlank(rua, "rua");
        this.numero = this.notBlank(numero, "numero");
        this.cidade = this.notBlank(cidade, "cidade");
        this.bairro = this.notBlank(bairro, "bairro");
        this.estado = this.notBlank(estado, "estado");
    }

    private String notBlank(String field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
        return field;
    }
}