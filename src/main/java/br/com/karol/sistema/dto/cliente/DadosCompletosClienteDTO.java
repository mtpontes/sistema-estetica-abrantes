package br.com.karol.sistema.dto.cliente;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosCompletosClienteDTO {

    private Long id;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;


    // talvez o Bean Utils deixe o `enderecos` com null ou uma lista vazia
    public DadosCompletosClienteDTO(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
    }
}