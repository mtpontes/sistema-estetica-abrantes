package br.com.karol.sistema.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Agendamento")
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        this.setTipoProcedimento(tipoProcedimento);
        this.setObservacao(observacao);
        this.setCliente(cliente);
        this.setDataHora(dataHora);
        this.setUsuario(usuario);
        this.dataCriacao = LocalDateTime.now();
    }


    public void remarcarAgendamento(String observacao, LocalDateTime dataHora) {
        this.observacao = observacao; // pode ser blank
        this.setDataHora(dataHora);
    }

    private void notNull(Object obj, String nomeCampo) {
        if (obj == null) throw new IllegalArgumentException("Não pode ser nulo: " + nomeCampo);
    }

    public void setTipoProcedimento(Procedimento tipoProcedimento) {
        this.notNull(tipoProcedimento, "tipoProcedimento");
        this.tipoProcedimento = tipoProcedimento;
    }
    public void setObservacao(String observacao) {
        // Observação: pode ser blank
        this.observacao = observacao == null ? "" : observacao;
    }
    public void setCliente(Cliente cliente) {
        this.notNull(cliente, "cliente");
        this.cliente = cliente;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.notNull(dataHora, "dataHora");
        this.dataHora = dataHora;
    }
    public void setUsuario(Usuario usuario) {
        this.notNull(usuario, "usuario");
        this.usuario = usuario;
    }
}