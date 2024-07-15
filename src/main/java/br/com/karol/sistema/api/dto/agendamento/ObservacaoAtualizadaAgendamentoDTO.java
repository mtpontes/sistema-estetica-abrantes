package br.com.karol.sistema.api.dto.agendamento;

import br.com.karol.sistema.domain.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ObservacaoAtualizadaAgendamentoDTO {

    private Long id;
    private String observacao;

    public ObservacaoAtualizadaAgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.observacao = agendamento.getObservacao();
    }
}