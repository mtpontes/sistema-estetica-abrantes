package br.com.karol.sistema.dto.administrador;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Administrador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosAdministradorDTO {

    private Integer id;
    private String usuario;


    public DadosAdministradorDTO(Administrador administrador) {
        BeanUtils.copyProperties(administrador, this);
    }
}