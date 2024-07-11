package br.com.karol.sistema.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioDTO {

    @NotBlank
    private String nome;
    
    @NotBlank
    @Size(min = 3)
    private String login;

    @NotBlank
    @Size(min = 8)
    private String senha;
}