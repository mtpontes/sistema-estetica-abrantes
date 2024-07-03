package br.com.karol.sistema.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Procedimento")
@Table(name = "procedimentos")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double valor;

    public Procedimento(String nome, String descricao, Double valor) {
        this.setAll(nome, descricao, valor);
    }
    

    public void atualizarDados(String nome, String descricao, Double valor) {
        if (!this.isBlank(nome))
            this.setNome(nome);

        if (!this.isBlank(descricao))
            this.setDescricao(descricao);

        if (!this.isNull(valor))
            this.setValor(valor);
    }

    public void setNome(String nome) {
        this.notBlank(nome, "nome");
        this.nome = nome;
    }
    public void setDescricao(String descricao) {
        this.notBlank(descricao, "descricao");
        this.descricao = descricao;
    }
    public void setValor(Double valor) {
        this.isValidValor(valor);
        this.valor = valor;
    }

    private boolean isNull(Object param) {
        if (param == null) return true;
        return false;
    }
    private boolean isBlank(String param) {
        if (param.isBlank()) return true; 
        return false;
    }

    private void notNull(Object param, String nomeCampo) {
        if (this.isNull(param)) 
            throw new IllegalArgumentException("Não pode ser null: " + nomeCampo);
    }
    private void notBlank(String param, String fieldName) {
        this.notNull(param, fieldName);
        if (this.isBlank(param))
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
    }
    // Sofrera alteracao no futuro
    private boolean isValidValor(Double valor) {
        this.notNull(valor, "valor");
        if (valor < 50.00) return false;
        return true;
    }

    private void setAll(String nome, String descricao, Double valor) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setValor(valor);
    }
}