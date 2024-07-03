package br.com.karol.sistema.dto.cliente;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdNomeEmailClienteDTO {

    private Long id;
    private String nome;
    private String email;

    public IdNomeEmailClienteDTO(Cliente cliente) {
        BeanUtils.copyProperties(cliente, this);
    }
}