package br.com.karol.sistema.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Procedimento tipoProcedimento;
    private String observacao;

    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    
    @Column(name = "Data_Hora")
    private LocalDateTime dataHora;
    
    @Column(name = "Data_Criacao")
    private LocalDateTime dataCriacao;
    
    @ManyToOne
    private Usuario usuario;


    public Agendamento(Procedimento tipoProcedimento, String observacao, Cliente cliente, LocalDateTime dataHora, Usuario usuario) {
        this.tipoProcedimento = tipoProcedimento;
        this.observacao = observacao;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.usuario = usuario;

        this.dataCriacao = LocalDateTime.now();
    }
}