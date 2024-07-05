package br.com.karol.sistema.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "procedimentos", collation = "pt", language = "portuguese")
public class Procedimento {

    @Id
    private String id;
    @Indexed(unique = true)
    private String nome;
    private String descricao;
    private Double valor;

    private static final Double VALOR_MINIMO = 50.00;

    public Procedimento(String nome, String descricao, Double valor) {
        this.setAllWithValidations(nome, descricao, valor);
    }
    

    public void atualizarDados(String nome, String descricao, Double valor) {
        if (!this.isBlank(nome)) this.nome = nome;
        if (!this.isBlank(descricao)) this.descricao = descricao;
        if (!this.isValidValor(valor)) this.valor = valor;
    }

    private boolean isNull(Object param) {
        return param == null ? true : false;
    }
    private boolean isBlank(String param) {
        if (this.isNull(param) || param.isBlank()) return true; 
        return false;
    }

    private void notNull(Object param, String fieldName) {
        if (this.isNull(param)) 
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
    }
    private void notBlank(String param, String fieldName) {
        this.notNull(param, fieldName);
        if (this.isBlank(param))
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }

    // Sofrerá alteracao no futuro
    private boolean isValidValor(Double valor) {
        this.notNull(valor, "valor");
        if (valor < VALOR_MINIMO) return false;
        return true;
    }

    private void setAllWithValidations(String nome, String descricao, Double valor) {
        this.notBlank(nome, "nome");
        this.nome = nome;

        this.notBlank(descricao, "descricao");
        this.descricao = descricao;

        this.isValidValor(valor);
        this.valor = valor;
    }
}