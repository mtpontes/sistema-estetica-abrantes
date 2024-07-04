package br.com.karol.sistema.dto.procedimento;

import br.com.karol.sistema.domain.Procedimento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosProcedimentoDTO {

    private String id;
    private String nome;
    private String descricao;
    private Double valor;

    public DadosProcedimentoDTO(Procedimento dados) {
        this.id = dados.getId();
        this.nome = dados.getNome();
        this.descricao = dados.getDescricao();
        this.valor = dados.getValor();
    }
}