package br.com.karol.sistema.dto.cliente;

import br.com.karol.sistema.domain.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarClienteDTO {

    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;
}