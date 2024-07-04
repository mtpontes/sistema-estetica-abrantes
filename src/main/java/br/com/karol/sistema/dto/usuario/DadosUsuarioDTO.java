package br.com.karol.sistema.dto.usuario;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosUsuarioDTO {

    private Long id;
    private String nome;
    private UserRole role;

    public DadosUsuarioDTO(Usuario usuario) {
        BeanUtils.copyProperties(usuario, this);
    }
}