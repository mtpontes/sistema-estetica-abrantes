package br.com.karol.sistema.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Administrador")
@Table(name = "administradores")
public class Administrador {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String usuario;
    private String senha;


    public Administrador(String usuario, String senha) {
        this.usuario = usuario;
        this.senha= senha;
    }

    public void update(String usuario) {
        if (usuario != null && !usuario.isBlank()) {
            this.usuario = usuario;
        }
    }
}