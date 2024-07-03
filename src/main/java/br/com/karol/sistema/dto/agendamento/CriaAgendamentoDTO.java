package br.com.karol.sistema.dto.agendamento;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.karol.sistema.dto.procedimento.CriarProcedimentoDTO;
import jakarta.validation.Valid;
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
public class CriaAgendamentoDTO {

    @NotNull 
    @Valid
    private CriarProcedimentoDTO procedimento;

    private String observacao; // pode ser blank

    @NotNull
    private Long idCliente;
    
    @NotNull 
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime dataHora;
}