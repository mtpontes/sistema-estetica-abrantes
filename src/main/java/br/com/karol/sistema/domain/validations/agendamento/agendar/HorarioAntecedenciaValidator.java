package br.com.karol.sistema.domain.validations.agendamento.agendar;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;

@Component
public class HorarioAntecedenciaValidator implements AgendamentoValidator {

    private final Integer ANTECEDENCIA_MINIMA_EM_MINUTOS = 90;

    
    @Override
    public void validate(Agendamento dados) {
        var dataAgendamento = dados.getDataHora();
        var agora = LocalDateTime.now();
        var result = Duration.between(dataAgendamento, agora).toMinutes();

        if (result < 90)
            throw new IllegalArgumentException("O agendamento deve ser feito com antecedência mínima de " + ANTECEDENCIA_MINIMA_EM_MINUTOS + " minutos");
    }
}