package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true,nullable=false)
    private String usuario;
    private String senha;

}
