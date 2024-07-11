package br.com.karol.sistema.api.dto.agendamento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarObservacaoAgendamentoDTO {

    @NotBlank
    private String observacao;
}