package br.com.karol.sistema.dto.procedimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CriarProcedimentoDTO {

    private String nome;
    private String descricao;
    private Double valor;
}