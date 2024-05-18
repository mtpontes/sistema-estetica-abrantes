package br.com.karol.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cliente {
    @Id
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

}
