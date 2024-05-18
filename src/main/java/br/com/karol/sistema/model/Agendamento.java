package br.com.karol.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Agendamento {
    @Id
    private Integer id;
    @ManyToOne
    private Procedimento nomeProcedimento;
    @ManyToOne
    private Cliente cliente;
    private LocalDate data;
    private LocalTime hora;


}
