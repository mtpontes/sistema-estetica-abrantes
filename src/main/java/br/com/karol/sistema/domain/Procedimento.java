package br.com.karol.sistema.domain;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Procedimento")
@Table(name = "procedimentos")
public class Procedimento {
    
    private static final Double VALOR_MINIMO = 50.00;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    
    private String descricao;
    private LocalTime duracao;
    private Double valor;

    public Procedimento(
        String nome, 
        String descricao, 
        LocalTime duracao, 
        Double valor
    ) {
        this.nome = this.notBlank(nome, "nome");
        this.descricao = this.notBlank(descricao, "descricao");
        this.duracao = this.notNull(duracao, "duracao");
        
        this.isValidValor(valor);
        this.valor = this.notNull(valor, "valor");
    }
    

    public void atualizarDados(
        String nome, 
        String descricao, 
        LocalTime duracao, 
        Double valor
    ) {
        if (!this.isBlank(nome)) this.nome = nome;
        if (!this.isBlank(descricao)) this.descricao = descricao;
        if (!this.isNull(duracao)) this.duracao = duracao;
        if (!this.isValidValor(valor)) this.valor = valor;
    }

    private boolean isNull(Object param) {
        return param == null;
    }
    private boolean isBlank(String param) {
        if (this.isNull(param) || param.isBlank()) return true; 
        return false;
    }

    private <T> T notNull(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        return field;
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
    private boolean isValidValor(Double valor) {
        if (this.isNull(valor) || valor == 0.00) return true;
        this.validateValor(valor);
        return false;
    }
}