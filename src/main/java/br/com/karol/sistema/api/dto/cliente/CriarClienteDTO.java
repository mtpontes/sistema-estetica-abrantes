package br.com.karol.sistema.api.dto.cliente;

import br.com.karol.sistema.api.dto.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CriarClienteDTO {

    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private EnderecoDTO endereco;
}