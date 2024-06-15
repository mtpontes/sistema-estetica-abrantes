package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data

public class ClienteDTO {
    private Integer id;

    @NotBlank(message="Campo obrigatório!")
    private String cpf;

    @NotBlank(message = "Campo Obrigatório")
    private String nome;

    @NotBlank(message="Campo obrigatório!")
    private String telefone;

    @NotBlank(message="Campo obrigatório!")
    private String email;
    private List<EnderecoDTO> enderecos;


}
