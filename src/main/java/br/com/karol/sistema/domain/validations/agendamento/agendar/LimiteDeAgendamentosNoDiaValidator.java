package br.com.karol.sistema.domain.validations.agendamento.agendar;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.repository.AgendamentoRepository;

@Component
public class LimiteDeAgendamentosNoDiaValidator implements AgendamentoValidator {

    private final Integer LIMITE_AGENDAMENTOS_NO_DIA = 2;
    private AgendamentoRepository repository;

    public LimiteDeAgendamentosNoDiaValidator(AgendamentoRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(Agendamento dados) {
        var dataAgendamento = dados.getDataHora().toLocalDate();

        var result = repository.countByClienteAndDataHoraBetween(
            dados.getCliente().getId(),
            dataAgendamento.atStartOfDay(),
            dataAgendamento.atTime(23, 59, 59));

        if (result >= LIMITE_AGENDAMENTOS_NO_DIA)
            throw new IllegalArgumentException("Cliente atingiu o limite de agendamentos no dia");
    }
}