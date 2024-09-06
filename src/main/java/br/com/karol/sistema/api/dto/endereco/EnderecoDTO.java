package br.com.karol.sistema.api.dto.endereco;

import br.com.karol.sistema.domain.Endereco;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String rua;
    @NotBlank
    private String numero;
    @NotBlank
    private String cidade;
    @NotBlank
    private String bairro;
    @NotBlank
    private String estado;

    public EnderecoDTO(Endereco e) {
        this.rua = e.getRua();
        this.numero = e.getNumero();
        this.cidade = e.getCidade();
        this.bairro = e.getBairro();
        this.estado = e.getEstado();
    }
}