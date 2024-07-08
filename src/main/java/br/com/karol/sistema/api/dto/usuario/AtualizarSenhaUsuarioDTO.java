package br.com.karol.sistema.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarSenhaUsuarioDTO {

    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
}