package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Login {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(unique=true, nullable=false)
    private String login;
    private String password;

}
