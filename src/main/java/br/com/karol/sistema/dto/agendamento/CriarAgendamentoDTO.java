package br.com.karol.sistema.dto.agendamento;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

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
public class CriarAgendamentoDTO {

    @NotNull 
    private String procedimentoId;
    
    @NotNull
    private String clienteId;
    
    @NotNull 
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime dataHora;
    
    private String observacao; // pode ser blank
}