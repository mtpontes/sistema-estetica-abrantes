package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Procedimento nomeProcedimento;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    private LocalDate data;
    private LocalTime hora;


}
