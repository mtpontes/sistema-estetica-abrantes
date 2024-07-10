package br.com.karol.sistema.domain.constants;

import java.time.LocalTime;

public final class AgendamentoConstants {
    public static final LocalTime HORARIO_ABERTURA = LocalTime.of(8, 0);
    public static final LocalTime HORARIO_FECHAMENTO = LocalTime.of(18, 0);
    
    public static final int LIMITE_AGENDAMENTOS_NO_DIA = 2;
    public static final int INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS = 30;
    public static final int ANTECEDENCIA_MINIMA_EM_MINUTOS = 90;
}