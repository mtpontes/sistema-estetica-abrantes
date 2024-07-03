package br.com.karol.sistema.dto.usuario;

import jakarta.validation.constraints.NotBlank;
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
    private String login;

    @NotBlank
    private String senha;
}