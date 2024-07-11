package br.com.karol.sistema.domain;

import java.time.LocalTime;
import java.util.Objects;

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
        this.nome = this.notBlank(nome, "nome");
        this.descricao = this.notBlank(descricao, "descricao");
        this.duracao = this.notNull(duracao, "duracao");
        
        this.isValidValorForUpdate(valor);
        this.valor = this.notNull(valor, "valor");
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

    private <T> T notNull(T param, String fieldName) {
        return Objects.requireNonNull(param, "Não pode se null: " + fieldName);
    }
    private String notBlank(String param, String fieldName) {
        this.notNull(param, fieldName);
        if (this.isBlank(param))
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
        return param;
    }

    private void validateValor(Double valor) {
        if (valor < VALOR_MINIMO) 
            throw new IllegalArgumentException("Não é possível definir um valor menor que " + VALOR_MINIMO);
    }
    private boolean isValidValorForUpdate(Double valor) {
        if (this.isNull(valor) || valor == 0.00) return true;
        this.validateValor(valor);
        return false;
    }
}