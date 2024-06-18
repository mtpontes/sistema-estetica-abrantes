package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Procedimento nomeProcedimento;

    private String observacao;

    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    @Column(name = "Data_Hora")
    private LocalDateTime dataHora;
    @Column(name = "Data_Criacao")
    private LocalDateTime dataCriacao;
    @ManyToOne
    private Usuario usuario;


}
