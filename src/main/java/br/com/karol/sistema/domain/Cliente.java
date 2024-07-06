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
@Document(collection = "clientes", collation = "pt", language = "portuguese")
public class Cliente {

    @Id    
    private String id;
    
    @Indexed(unique = true)
    private String cpf;
    private String nome;
    
    @Indexed(unique = true)
    private String telefone;
    
    @Indexed(unique = true)
    private String email;
    private Endereco endereco;

    public Cliente(String cpf, String nome, String telefone, String email, Endereco endereco) {
        this.setAllWithValidations(cpf, nome, telefone, email, endereco);
    }

    
    public void atualizarDados(String nome, String telefone, String email, Endereco endereco) {
        if (!this.isBlank(nome)) this.nome = nome;
        if (!this.isBlank(telefone)) this.telefone = telefone;
        if (!this.isBlank(email)) this.email = email;

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
        return param == null;
    }
    private boolean isBlank(String param) {
        return this.isNull(param) || param.isBlank();
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

    private void setAllWithValidations(String cpf, String nome, String telefone, String email, Endereco endereco) {
        this.notBlank(cpf, "cpf");
        this.cpf = cpf;

        this.notBlank(nome, "nome");
        this.nome = nome;

        this.notBlank(telefone, "telefone");
        this.telefone = telefone;

        this.notBlank(email, "email");
        this.email = email;

        this.notNull(endereco, "endereco");
        this.endereco = endereco;
    }
}