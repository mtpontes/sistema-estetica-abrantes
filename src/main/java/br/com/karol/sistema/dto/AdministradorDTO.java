package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Administrador;
import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data

public class AdministradorDTO {

    private Integer id;
    private String nome;
    private String senha;


    public AdministradorDTO(Administrador administrador) {
        BeanUtils.copyProperties(administrador, this);
    }

}
