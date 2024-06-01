package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Integer id;
    private String cpf;
    @Column(unique=true)
    private String nome;
    private String endereco;
    @Column(unique=true)
    private String telefone;
    private String email;

}
