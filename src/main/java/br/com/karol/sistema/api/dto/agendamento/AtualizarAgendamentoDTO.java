package br.com.karol.sistema.api.dto.agendamento;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarAgendamentoDTO {

    @NotBlank
    private String observacao;
    @NotNull 
    @Future
    private LocalDateTime dataHora;
}