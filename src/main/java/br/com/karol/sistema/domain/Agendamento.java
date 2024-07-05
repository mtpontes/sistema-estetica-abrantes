package br.com.karol.sistema.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Setter
    private LocalDateTime dataHora;
    private LocalDateTime dataCriacao;
    @DBRef
    private Usuario usuario;

    public Agendamento(Procedimento procedimento, String observacao, Cliente cliente, LocalDateTime dataHora, Usuario usuario) {
        this.setAllWithValidations(procedimento, observacao, cliente, dataHora, usuario);
    }


    public void remarcarAgendamento(String observacao, LocalDateTime dataHora) {
        this.observacao = observacao; // pode ser blank
        this.setDataHora(dataHora);
    }

    private void notNull(Object obj, String nomeCampo) {
        if (obj == null) throw new IllegalArgumentException("Não pode ser nulo: " + nomeCampo);
    }

    private void setAllWithValidations(Procedimento procedimento, String observacao, Cliente cliente, LocalDateTime dataHora, Usuario usuario) {
        this.notNull(procedimento, "procedimento");
        this.procedimento = procedimento;

        this.observacao = observacao == null ? "" : observacao;

        this.notNull(cliente, "cliente");
        this.cliente = cliente;

        this.notNull(dataHora, "dataHora");
        this.dataHora = dataHora;

        this.notNull(usuario, "usuario");
        this.usuario = usuario;

        this.dataCriacao = LocalDateTime.now();
    }
}