package br.com.karol.sistema.domain.validations.agendamento.agendar;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;

@Component
public class LimiteDeAgendamentosNoDiaValidator implements AgendamentoValidator {

    private static final Integer LIMITE_AGENDAMENTOS_NO_DIA = 2;
    private AgendamentoRepository repository;

    public LimiteDeAgendamentosNoDiaValidator(AgendamentoRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(Agendamento dados) {
        LocalDate dataAgendamento = dados.getDataHora().toLocalDate();

        Long result = repository.countByClienteAndDataHoraBetween(
            dados.getCliente().getId(),
            dataAgendamento.atStartOfDay(),
            dataAgendamento.atTime(23, 59, 59));

        if (result != null && result >= LIMITE_AGENDAMENTOS_NO_DIA)
            throw new IllegalArgumentException("Cliente atingiu o limite de agendamentos no dia");
    }
}