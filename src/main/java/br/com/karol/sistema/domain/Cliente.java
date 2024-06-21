package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name="Clientes")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String cpf;

    private String nome;
    private String telefone;

    @Column(unique = true)
    private String email;

    @JoinColumn(name = "paciente_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();
}