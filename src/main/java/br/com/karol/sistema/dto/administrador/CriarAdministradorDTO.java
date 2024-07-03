package br.com.karol.sistema.dto.administrador;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Administrador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarAdministradorDTO {

    private String usuario;
    private String senha;

    public CriarAdministradorDTO(Administrador administrador) {
        BeanUtils.copyProperties(administrador, this);
    }
}