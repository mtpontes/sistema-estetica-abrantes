package br.com.karol.sistema.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;


    public Cliente(String cpf, String nome, String telefone, String email, Endereco endereco) {
        this.setAll(cpf, nome, telefone, email, endereco);
    }

    public void atualizarDados(String nome, String telefone, String email, Endereco endereco) {
        if (!this.isBlank(nome))
            this.nome = nome;

        if (!this.isBlank(telefone))
            this.telefone = telefone;

        if (!this.isBlank(email))
            this.email = email;

        if (!this.isNull(endereco)) {
            if (this.endereco != null) {
                this.endereco.atualizarDados(
                    endereco.getRua(),
                    endereco.getNumero(), 
                    endereco.getCidade(), 
                    endereco.getBairro(), 
                    endereco.getEstado()
                );
                return;
            }
            this.endereco = endereco; 
        }
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

    public void setCpf(String cpf) {
        this.notBlank(cpf, "cpf");
        this.cpf = cpf;
    }
    public void setNome(String nome) {
        this.notBlank(nome, "nome");
        this.nome = nome;
    }
    public void setTelefone(String telefone) {
        this.notBlank(telefone, "telefone");
        this.telefone = telefone;
    }
    public void setEmail(String email) {
        this.notBlank(email, "email");
        this.email = email;
    }
    public void setEndereco(Endereco endereco) {
        this.notNull(endereco, "endereco");
        this.endereco = endereco;
    }

    private void setAll(String cpf, String nome, String telefone, String email, Endereco endereco) {
        this.setCpf(cpf);
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
    }
}