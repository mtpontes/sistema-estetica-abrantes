package br.com.karol.sistema.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data

public class AdministradorDTO {

    private Integer id;
    private String nome;
    private String senha;

}
