package br.com.karol.sistema.domain;

import java.time.LocalDateTime;
import java.util.List;

import br.com.karol.sistema.domain.enums.StatusAgendamento;
import br.com.karol.sistema.domain.validator.agendamento.AgendamentoValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Agendamento")
@Table(name = "agendamentos")
public class Agendamento {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String observacao;
    
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;
    
    @Setter
    private LocalDateTime dataHora;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id")
    private Procedimento procedimento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    private String usuarioLogin;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public Agendamento(
        Procedimento procedimento, 
        StatusAgendamento status, 
        String observacao, 
        Cliente cliente, 
        LocalDateTime dataHora, 
        List<AgendamentoValidator> validators
    ) {
        this.procedimento = this.notNull(procedimento, "procedimento");

        List<StatusAgendamento> statusesPermitidos = List.of(StatusAgendamento.PENDENTE, StatusAgendamento.CONFIRMADO);
        if (!statusesPermitidos.contains(status))
            throw new IllegalArgumentException(
                "Não é possível criar um agendamento com status diferente de: " + statusesPermitidos.toString());
        this.status = status;

        this.setObservacao(observacao);
        
        this.cliente = this.notNull(cliente, "cliente");

        this.mustBeBeforeNow(dataHora);
        this.dataHora = this.notNull(dataHora, "dataHora");

        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = dataCriacao;

        if (validators.isEmpty()) 
            throw new RuntimeException("Deve fornecer um ou mais validadores");
        validators.forEach(validator -> validator.validate(this));
    }
    public Agendamento(
        Procedimento procedimento, 
        StatusAgendamento status, 
        String observacao, 
        Cliente cliente, 
        LocalDateTime dataHora, 
        List<AgendamentoValidator> validators, 
        String usuarioLogin
    ) {
        this(procedimento, status, observacao, cliente, dataHora, validators);
        if (this.notNull(usuarioLogin, "usuarioLogin").isBlank())
            throw new IllegalArgumentException("Não pode ser blank: usuarioLogin");
        this.usuarioLogin = usuarioLogin;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null ? "" : observacao; // pode ser blank

        this.atualizarDataModificacao();
    }

    public void remarcar(LocalDateTime novaDataHora, List<AgendamentoValidator> validators) {
        this.dataHora = novaDataHora;
        this.mustBeBeforeNow(novaDataHora);

        validators.forEach(validator -> validator.validate(this));
        this.atualizarDataModificacao();
    }

    public void atualizarStatus(StatusAgendamento novoStatus) {
        this.status.validateTransition(novoStatus);
        this.status = novoStatus;

        this.atualizarDataModificacao();
    }

    private void atualizarDataModificacao() {
        this.dataModificacao = LocalDateTime.now(); 
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