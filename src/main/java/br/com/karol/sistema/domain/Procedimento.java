package br.com.karol.sistema.domain;

import java.time.LocalTime;

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
    
    private static final Double VALOR_MINIMO = 50.00;
    
    @Id
    private String id;
    @Indexed(unique = true)
    private String nome;
    private String descricao;
    private LocalTime duracao;
    private Double valor;

    public Procedimento(String nome, String descricao, LocalTime duracao, Double valor) {
        this.notBlank(nome, "nome");
        this.nome = nome;

        this.notNull(duracao, "duracao");
        this.duracao = duracao;

        this.notBlank(descricao, "descricao");
        this.descricao = descricao;

        this.notNull(valor, "valor");
        this.isValidValorForUpdate(valor);
        this.valor = valor;
    }
    

    public void atualizarDados(String nome, String descricao, LocalTime duracao, Double valor) {
        if (!this.isBlank(nome)) this.nome = nome;
        if (!this.isBlank(descricao)) this.descricao = descricao;
        if (!this.isNull(duracao)) this.duracao = duracao;
        if (!this.isValidValorForUpdate(valor)) this.valor = valor;
    }

    private boolean isNull(Object param) {
        return param == null;
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

    private boolean isValidValorForUpdate(Double valor) {
        if (this.isNull(valor) || valor == 0.00) return true;
        if (valor < VALOR_MINIMO) 
            throw new IllegalArgumentException("Não é possível definir um valor menor que " + VALOR_MINIMO);
        return false;
    }
}