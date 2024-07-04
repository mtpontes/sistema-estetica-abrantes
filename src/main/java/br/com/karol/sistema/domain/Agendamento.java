package br.com.karol.sistema.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "agendamentos", collation = "pt", language = "portuguese")
public class Agendamento {

    @Id
    private String id;
    @DBRef
    private Procedimento procedimento;
    private String observacao;
    @DBRef
    private Cliente cliente;
    private LocalDateTime dataHora;
    private LocalDateTime dataCriacao;
    @DBRef
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

    public void setTipoProcedimento(Procedimento procedimento) {
        this.notNull(procedimento, "tipoProcedimento");
        this.procedimento = procedimento;
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