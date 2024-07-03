package br.com.karol.sistema.dto;

import org.springframework.beans.BeanUtils;

import br.com.karol.sistema.domain.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EnderecoDTO {

    private Long id;
    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;

    public EnderecoDTO(Endereco endereco) {
        BeanUtils.copyProperties(endereco, this);
    }
}