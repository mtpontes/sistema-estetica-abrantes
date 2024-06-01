package br.com.karol.sistema.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class AgendamentoDTO {

    private Integer id;
    private ProcedimentoDTO nomeProcedimento;
    private ClienteDTO cliente;
    private LocalDate data;
    private LocalTime hora;


}
