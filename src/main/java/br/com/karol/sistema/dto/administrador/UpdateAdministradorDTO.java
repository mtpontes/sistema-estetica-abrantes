package br.com.karol.sistema.dto.administrador;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Administrador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdministradorDTO {

    private String usuario;


    public UpdateAdministradorDTO(Administrador administrador) {
        BeanUtils.copyProperties(administrador, this);
    }
}