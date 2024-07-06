package br.com.karol.sistema.dto.procedimento;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarProcedimentoDTO {

    private String nome;
    private String descricao;
    private LocalTime duracao;
    private Double valor;
}