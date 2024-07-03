package br.com.karol.sistema.dto.administrador;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Administrador;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarAdministradorDTO {

    @NotBlank
    private String usuario;


    public AtualizarAdministradorDTO(Administrador administrador) {
        BeanUtils.copyProperties(administrador, this);
    }
}