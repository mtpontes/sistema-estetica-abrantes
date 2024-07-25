package br.com.karol.sistema.domain.constants;

import java.time.LocalTime;
import java.util.List;

import br.com.karol.sistema.domain.enums.StatusAgendamento;

public final class AgendamentoConstants {
    public static final LocalTime HORARIO_ABERTURA = LocalTime.of(8, 0);
    public static final LocalTime HORARIO_FECHAMENTO = LocalTime.of(18, 0);
    
    public static final int LIMITE_AGENDAMENTOS_NO_DIA = 2;
    public static final int INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS = 30;
    public static final int ANTECEDENCIA_MINIMA_EM_MINUTOS = 90;

    public static final List<StatusAgendamento> statusesPermitidosParaClients = 
        List.of(StatusAgendamento.CONFIRMADO, StatusAgendamento.CANCELADO);
}