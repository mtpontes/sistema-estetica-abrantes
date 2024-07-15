package br.com.karol.sistema.domain.validator.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.constants.AgendamentoConstants;

@Component
public class HorarioAntecedenciaValidator implements AgendamentoValidator {
    
    private static final int ANTECEDENCIA = AgendamentoConstants.ANTECEDENCIA_MINIMA_EM_MINUTOS;

    
    @Override
    public void validate(Agendamento dados) {
        var dateTimeNovoAgendamento = dados.getDataHora();
        var agora = LocalDateTime.now();

        if (dateTimeNovoAgendamento.isBefore(agora)) {
            throw new IllegalArgumentException("A data do agendamento não pode ser no passado.");
        }

        long result = Duration.between(agora, dateTimeNovoAgendamento).toMinutes();
        if (result < ANTECEDENCIA)
            throw new IllegalArgumentException("O agendamento deve ser feito com antecedência mínima de " +
            ANTECEDENCIA + " minutos");
    }
}