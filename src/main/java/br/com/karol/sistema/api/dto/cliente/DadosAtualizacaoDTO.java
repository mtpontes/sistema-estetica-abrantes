package br.com.karol.sistema.api.dto.cliente;

import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
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
public class DadosAtualizacaoDTO {

    private String nome;
    private Telefone telefone;
    private Email email;
}