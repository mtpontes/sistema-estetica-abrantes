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
    private String observacao;
    @Setter
    private LocalDateTime dataHora;
    @DBRef
    private Procedimento procedimento;
    @DBRef
    private Cliente cliente;
    @DBRef
    private Usuario usuario;
    private LocalDateTime dataCriacao;

    public Agendamento(Procedimento procedimento, String observacao, Cliente cliente, LocalDateTime dataHora, Usuario usuario) {
        this.observacao = observacao == null ? "" : observacao;

        this.notNull(dataHora, "dataHora");
        this.validateDataHora(dataHora);
        this.dataHora = dataHora;
        
        this.notNull(procedimento, "procedimento");
        this.procedimento = procedimento;

        this.notNull(cliente, "cliente");
        this.cliente = cliente;

        this.notNull(usuario, "usuario");
        this.usuario = usuario;

        this.dataCriacao = LocalDateTime.now();
    }


    public void remarcarAgendamento(String observacao, LocalDateTime dataHora) {
        this.observacao = observacao; // pode ser blank
        this.validateDataHora(dataHora);
        this.setDataHora(dataHora);
    }

    private void notNull(Object obj, String nomeCampo) {
        if (obj == null) throw new IllegalArgumentException("Não pode ser nulo: " + nomeCampo);
    }

    private void validateDataHora(LocalDateTime dataHora) {
        if (dataHora != null && dataHora.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Data e hora não podem ser no passado");
    }
}