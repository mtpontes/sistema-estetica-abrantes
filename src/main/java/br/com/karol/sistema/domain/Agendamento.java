package br.com.karol.sistema.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.validator.agendamento.AgendamentoValidator;
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
    private StatusAgendamento status;
    @Setter
    private LocalDateTime dataHora;
    @DBRef
    private Procedimento procedimento;
    @DBRef
    private Cliente cliente;
    @DBRef
    private Usuario usuario;
    private LocalDateTime dataCriacao;

    public Agendamento(Procedimento procedimento, StatusAgendamento status, String observacao, Cliente cliente, LocalDateTime dataHora, Usuario usuario, List<AgendamentoValidator> validators) {
        this.procedimento = this.notNull(procedimento, "procedimento");

        List<StatusAgendamento> statusesPermitidos = List.of(StatusAgendamento.PENDENTE, StatusAgendamento.CONFIRMADO);
        if (!statusesPermitidos.contains(status))
            throw new IllegalArgumentException("Não é possível criar um agendamento com status diferente de: " + statusesPermitidos.toString());
        this.status = status;

        this.setObservacao(observacao);
        
        this.cliente = this.notNull(cliente, "cliente");

        this.mustBeBeforeNow(dataHora);
        this.dataHora = this.notNull(dataHora, "dataHora");

        this.usuario = this.notNull(usuario, "usuario");
        this.dataCriacao = LocalDateTime.now();

        validators.forEach(validator -> validator.validate(this));
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null ? "" : observacao; // pode ser blank
    }

    public void remarcar(LocalDateTime novaDataHora, List<AgendamentoValidator> validators) {
        this.dataHora = novaDataHora;
        this.mustBeBeforeNow(novaDataHora);
        validators.forEach(validator -> validator.validate(this));
    }

    public void atualizarStatus(StatusAgendamento novoStatus) {
        this.status.validateTransition(novoStatus);
        this.status = novoStatus;
    }

    private <T> T notNull(T field, String fieldName) {
        if (field == null)
            throw new IllegalArgumentException("Não pode ser null: " + fieldName);
        return field;
    }

    private void mustBeBeforeNow(LocalDateTime dataHora) {
        if (dataHora != null && dataHora.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Data e hora não podem ser no passado");
    }
}