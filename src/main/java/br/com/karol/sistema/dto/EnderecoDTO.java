package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Endereco;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;


@Data
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
    public EnderecoDTO() {}


}