package br.com.karol.sistema.api.dto.agendamento;

import java.time.LocalDateTime;

import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
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
public class MeDadosAgendamentoDTO {

    private Long id;
    private StatusAgendamento status;
    private String observacao;
    private LocalDateTime dataHora;
    private DadosProcedimentoDTO procedimento;

    public MeDadosAgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.status = agendamento.getStatus();
        this.procedimento = new DadosProcedimentoDTO(agendamento.getProcedimento());
        this.observacao = agendamento.getObservacao();
        this.dataHora = agendamento.getDataHora();
    }
}