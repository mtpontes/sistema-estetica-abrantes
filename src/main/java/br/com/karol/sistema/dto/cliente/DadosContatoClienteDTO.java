package br.com.karol.sistema.dto.cliente;

import br.com.karol.sistema.domain.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosContatoClienteDTO {

    private String id;
    private String nome;
    private String telefone;
    private String email;

    public DadosContatoClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
    }
}