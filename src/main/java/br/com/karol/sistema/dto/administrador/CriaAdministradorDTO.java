package br.com.karol.sistema.dto.administrador;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriaAdministradorDTO {

    private Integer id;
    private String usuario;
    private String senha;


    public CriaAdministradorDTO(UpdateAdministradorDTO administrador) {
        BeanUtils.copyProperties(administrador, this);
    }
}