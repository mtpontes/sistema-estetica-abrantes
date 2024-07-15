package br.com.karol.sistema.api.dto.agendamento;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusAtualizadoAgendamentoDTO {

    private Long id;
    private StatusAgendamento status;

    public StatusAtualizadoAgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.status = agendamento.getStatus();
    }
}