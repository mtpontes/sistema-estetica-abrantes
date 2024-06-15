package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Agendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data

public class AgendamentoDTO {

    private Integer id;

    @NotBlank(message="Campo obrigatório!")
    private ProcedimentoDTO nomeProcedimento;
    private ClienteDTO cliente;
    @NotBlank(message="Campo obrigatório!")
    @Future //Evita que agendem atendimento com datas passadas
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime dataHora;

    public AgendamentoDTO(Agendamento agendamento) {
        BeanUtils.copyProperties(agendamento, this);
    }


}
