package br.com.karol.sistema.dto.usuario;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.enums.UserRole;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosUsuarioDTO {

    private Integer id;
    private String nome;
    private UserRole role;

    public DadosUsuarioDTO(Usuario usuario) {
        BeanUtils.copyProperties(usuario, this);
    }
}