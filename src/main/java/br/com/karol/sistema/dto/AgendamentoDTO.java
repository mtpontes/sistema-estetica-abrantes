package br.com.karol.sistema.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class AgendamentoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private ProcedimentoDTO nomeProcedimento;
    @ManyToOne(cascade = CascadeType.ALL)
    private ClienteDTO cliente;
    private LocalDate data;
    private LocalTime hora;


}
