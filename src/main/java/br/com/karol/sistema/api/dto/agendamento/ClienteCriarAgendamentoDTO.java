package br.com.karol.sistema.api.dto.agendamento;

import java.time.LocalDateTime;

import br.com.karol.sistema.domain.enums.StatusAgendamento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCriarAgendamentoDTO {

    @NotNull
    private Long procedimentoId;
    @NotNull
    private StatusAgendamento status;
    private String observacao; // pode ser blank
    @NotNull 
    @Future
    private LocalDateTime dataHora;
}