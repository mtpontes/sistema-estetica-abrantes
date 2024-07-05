package br.com.karol.sistema.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;

    public Endereco(String rua, String numero, String cidade, String bairro, String estado) {
        this.setAllWithValidations(rua, numero, cidade, bairro, estado);
    }

    
    public void atualizarDados(String rua, String numero, String cidade, String bairro, String estado) {
        if (!this.isBlank(rua)) this.rua = rua;
        if (!this.isBlank(numero)) this.numero = numero;
        if (!this.isBlank(cidade)) this.cidade = cidade;
        if (!this.isBlank(bairro)) this.bairro = bairro;
        if (!this.isBlank(estado)) this.estado = estado;
	}

    private boolean isBlank(String param) {
        return param == null || param.isBlank();
    }
    private void notBlank(String field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }

    private void setAllWithValidations(String rua, String numero, String cidade, String bairro, String estado) {
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
}