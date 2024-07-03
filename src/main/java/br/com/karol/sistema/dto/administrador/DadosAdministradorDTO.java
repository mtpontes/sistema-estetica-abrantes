package br.com.karol.sistema.dto.administrador;

import br.com.karol.sistema.domain.Administrador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosAdministradorDTO {

    private Long id;
    private String usuario;


    public DadosAdministradorDTO(Administrador administrador) {
        this.id = administrador.getId();
        this.usuario = administrador.getUsuario();
    }
}