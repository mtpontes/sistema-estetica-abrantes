package br.com.karol.sistema.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @OneToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Agendamento agendamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Procedimento procedimento;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario responsavelAgendamento;
    
    private String observacao;
    private LocalDate dataCadastro = LocalDate.now();
}