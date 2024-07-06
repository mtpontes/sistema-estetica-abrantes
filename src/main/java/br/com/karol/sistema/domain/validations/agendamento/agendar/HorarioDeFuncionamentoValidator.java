package br.com.karol.sistema.domain.validations.agendamento.agendar;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;

@Component
public class HorarioDeFuncionamentoValidator implements AgendamentoValidator {

    private final Integer HORA_ABERTURA = 8;
    private final Integer HORA_FECHAMENTO = 15;


    @Override
    public void validate(Agendamento dados) {
        var dataAgendamento = dados.getDataHora();
        var diaDaSemana = dataAgendamento.getDayOfWeek();

        boolean isDiaInvalido = diaDaSemana.equals(DayOfWeek.SATURDAY) || diaDaSemana.equals(DayOfWeek.SUNDAY);
        boolean isHoraInvalida = dataAgendamento.getHour() < HORA_ABERTURA && dataAgendamento.getHour() > HORA_FECHAMENTO;

        if (isDiaInvalido || isHoraInvalida)
            throw new IllegalArgumentException("Agendamento fora do horário de funcionamento.");
    }
}