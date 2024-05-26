package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @OneToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    private Agendamento agendamento;
    @OneToOne(cascade = CascadeType.ALL)
    private Procedimento procedimento;
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario responsavelAgendamento;
    private String observacao;

}
