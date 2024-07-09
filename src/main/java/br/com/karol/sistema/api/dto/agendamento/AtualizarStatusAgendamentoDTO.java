package br.com.karol.sistema.api.dto.agendamento;

import br.com.karol.sistema.domain.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarStatusAgendamentoDTO {

    @NotNull
    private StatusAgendamento status;
}