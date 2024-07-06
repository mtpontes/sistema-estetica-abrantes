package br.com.karol.sistema.api.dto.cliente;

import br.com.karol.sistema.domain.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosClienteDTO {

    private String id;
    private String cpf;
    private String nome;

    public DadosClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
    }
}