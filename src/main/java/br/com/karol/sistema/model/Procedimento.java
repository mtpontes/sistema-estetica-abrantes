package br.com.karol.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Procedimento {
    @Id
    private Integer id;
    private String nome;

}
