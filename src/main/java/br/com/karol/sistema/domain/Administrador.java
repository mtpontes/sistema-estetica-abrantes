package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( unique = true, nullable = false)
    private String usuario;
    private String senha;


    public void update(String usuario) {
        if (usuario != null && !usuario.isBlank()) {
            this.usuario = usuario;
        }
    }
}