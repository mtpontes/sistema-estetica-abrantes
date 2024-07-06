package br.com.karol.sistema.api.dto;

import br.com.karol.sistema.domain.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String estado;

    public EnderecoDTO(Endereco e) {
        this.rua = e.getRua();
        this.numero = e.getNumero();
        this.cidade = e.getCidade();
        this.bairro = e.getBairro();
        this.estado = e.getEstado();
    }
}