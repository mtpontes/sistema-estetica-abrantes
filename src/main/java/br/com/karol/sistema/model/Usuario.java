package br.com.karol.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    private Integer id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
    private String senha;

}
