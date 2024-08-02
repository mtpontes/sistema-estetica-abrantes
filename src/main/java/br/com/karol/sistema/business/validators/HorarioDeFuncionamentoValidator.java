package br.com.karol.sistema.business.validators;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.constants.AgendamentoConstants;
import br.com.karol.sistema.domain.validator.AgendamentoValidator;

@Component
public class HorarioDeFuncionamentoValidator implements AgendamentoValidator {

    private static final LocalTime ABERTURA = AgendamentoConstants.HORARIO_ABERTURA;
    private static final LocalTime FECHAMENTO = AgendamentoConstants.HORARIO_FECHAMENTO;


    @Override
    public void validate(Agendamento dados) {
        LocalDateTime dateTimeNovoAgendamento = dados.getDataHora();
        LocalTime horaNovoAgendamento = dateTimeNovoAgendamento.toLocalTime();
        DayOfWeek diaDaSemana = dateTimeNovoAgendamento.getDayOfWeek();

        LocalTime duracaoProcedimento = dados.getProcedimento().getDuracao();
        LocalTime terminoProcedimento = horaNovoAgendamento
            .plusHours(duracaoProcedimento.getHour())
            .plusMinutes(duracaoProcedimento.getMinute());

        boolean terminaDepoisDoFechamento = terminoProcedimento.isAfter(FECHAMENTO);
        if (terminaDepoisDoFechamento)
            throw new IllegalArgumentException("Duração do procedimento excede o horário de funcionamento");

        boolean isDiaInvalido = diaDaSemana.equals(DayOfWeek.SATURDAY) || diaDaSemana.equals(DayOfWeek.SUNDAY);
        boolean isHoraInvalida = horaNovoAgendamento.isBefore(ABERTURA) || horaNovoAgendamento.isAfter(FECHAMENTO);

        if (isDiaInvalido || isHoraInvalida)
            throw new IllegalArgumentException("Agendamento fora do horário de funcionamento");
    }
}