package br.com.karol.sistema.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes", collation = "pt", language = "portuguese")
public class Cliente {

    @Id    
    private String id;
    private String nome;
    @Indexed(unique = true)
    private Cpf cpf;
    @Indexed(unique = true)
    private Telefone telefone;
    @Indexed(unique = true)
    private Email email;
    private Endereco endereco;

    public Cliente(String nome, Cpf cpf, Telefone telefone, Email email, Endereco endereco) {
        this.nome = this.notBlank(nome, "nome");

        this.cpf = this.notNull(cpf, "cpf");
        this.telefone = this.notNull(telefone, "telefone");
        this.email = this.notNull(email, "email");
        this.endereco = this.notNull(endereco, "endereco");
    }

    
    public void atualizarDados(String nome, Telefone telefone, Email email) {
        if (!this.isNull(nome) && !nome.isBlank()) this.nome = nome;

        if (!this.isNull(telefone)) this.telefone = telefone;
        if (!this.isNull(email)) this.email = email;
    }

    public void atualizarEndereco(Endereco endereco) {
        if (!this.isNull(endereco)) this.endereco = endereco;
    }
    
    private boolean isNull(Object param) {
        return param == null;
    }

    private <T> T notNull(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        return field;
    }
    private String notBlank(String param, String fieldName) {
        this.notNull(param, fieldName);
        if (param.isBlank())
            throw new IllegalArgumentException("Não pode ser blank: " + fieldName);
        return param;
    }

    public String getCpf() {
        return this.cpf.getValue();
    }
    public String getTelefone() {
        return this.telefone.getValue();
    }
    public String getEmail() {
        return this.email.getValue();
    }
}