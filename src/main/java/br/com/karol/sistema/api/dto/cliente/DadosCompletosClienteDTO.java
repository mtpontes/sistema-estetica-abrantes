package br.com.karol.sistema.api.dto.cliente;

import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.domain.Cliente;
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
    private EnderecoDTO endereco;

    public DadosCompletosClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
        this.endereco = new EnderecoDTO(cliente.getEndereco());
    }
}