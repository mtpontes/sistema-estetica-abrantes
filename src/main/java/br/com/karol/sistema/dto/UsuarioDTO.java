package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data

public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String usuario;
    private String senha;

    public UsuarioDTO(Usuario usuario) {
        BeanUtils.copyProperties(usuario, this);
    }


}
