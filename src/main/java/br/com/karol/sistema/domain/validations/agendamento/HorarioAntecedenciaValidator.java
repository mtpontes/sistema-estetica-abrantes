package br.com.karol.sistema.domain.validations.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;

@Component
public class HorarioAntecedenciaValidator implements AgendamentoValidator {

    private static final Integer ANTECEDENCIA_MINIMA_EM_MINUTOS = 90;

    
    @Override
    public void validate(Agendamento dados) {
        var dataAgendamento = dados.getDataHora();
        var agora = LocalDateTime.now();

        if (dataAgendamento.isBefore(agora)) {
            throw new IllegalArgumentException("A data do agendamento não pode ser no passado.");
        }

        Long result = Duration.between(agora, dataAgendamento).toMinutes();
        if (result < ANTECEDENCIA_MINIMA_EM_MINUTOS)
            throw new IllegalArgumentException("O agendamento deve ser feito com antecedência mínima de " + ANTECEDENCIA_MINIMA_EM_MINUTOS + " minutos");
    }
}