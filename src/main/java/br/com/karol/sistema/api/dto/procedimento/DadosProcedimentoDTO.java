package br.com.karol.sistema.api.dto.procedimento;

import java.time.LocalTime;

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

    private Long id;
    private String nome;
    private String descricao;
    private LocalTime duracao;
    private Double valor;

    public DadosProcedimentoDTO(Procedimento dados) {
        this.id = dados.getId();
        this.nome = dados.getNome();
        this.descricao = dados.getDescricao();
        this.duracao = dados.getDuracao();
        this.valor = dados.getValor();
    }
}