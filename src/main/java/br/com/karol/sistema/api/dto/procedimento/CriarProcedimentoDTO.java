package br.com.karol.sistema.api.dto.procedimento;

import java.time.LocalTime;

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
    private LocalTime duracao;
    private Double valor;
}