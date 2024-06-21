package br.com.karol.sistema.dto.cliente;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Cliente;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosClienteDTO {

    private Integer id;
    private String nome;
    private String email;

    public DadosClienteDTO(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
    }
}