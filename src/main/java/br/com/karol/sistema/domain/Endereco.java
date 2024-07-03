package br.com.karol.sistema.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Endereco {

    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;

    public Endereco(String rua, String numero, String cidade, String bairro, String estado) {
        this.setAll(rua, numero, cidade, bairro, estado);
    }

    
    public void atualizarDados(String rua, String numero, String cidade, String bairro, String estado) {
        this.setAll(rua, numero, cidade, bairro, estado);
	}

    private void notBlank(String field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        if (field.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }

    public void setRua(String rua) {
        this.notBlank(rua, "rua");
        this.rua = rua;
    }
    public void setNumero(String numero) {
        this.notBlank(numero, "numero");
        this.numero = numero;
    }
    public void setCidade(String cidade) {
        this.notBlank(cidade, "cidade");
        this.cidade = cidade;
    }
    public void setBairro(String bairro) {
        this.notBlank(bairro, "bairro");
        this.bairro = bairro;
    }
    public void setEstado(String estado) {
        this.notBlank(estado, "estado");
        this.estado = estado;
    }
    
    private void setAll(String rua, String numero, String cidade, String bairro, String estado) {
        this.setRua(rua);
        this.setNumero(numero);
        this.setCidade(cidade);
        this.setBairro(bairro);
        this.setEstado(estado);
    }
}