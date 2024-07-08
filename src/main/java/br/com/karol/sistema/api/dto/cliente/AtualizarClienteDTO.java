package br.com.karol.sistema.api.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarClienteDTO {

    private String nome;
    private String telefone;
    private String email;
}