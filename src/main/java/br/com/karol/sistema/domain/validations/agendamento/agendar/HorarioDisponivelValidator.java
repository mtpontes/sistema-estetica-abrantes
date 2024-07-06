package br.com.karol.sistema.domain.validations.agendamento.agendar;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.repository.AgendamentoRepository;

@Component
public class HorarioDisponivelValidator implements AgendamentoValidator {

    private final Integer INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS = 30;
    private AgendamentoRepository repository;

    public HorarioDisponivelValidator(AgendamentoRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(Agendamento dados) {
        LocalTime inicioNovoAgendamento = dados.getDataHora().toLocalTime();
        List<Agendamento> agendamentos = repository.findByAgendamentosBetweenDataHora(
            dados.getDataHora().toLocalDate().atStartOfDay(), 
            dados.getDataHora().toLocalDate().atTime(23, 59, 59));

        agendamentos.forEach(a -> {
            LocalTime duracao = a.getProcedimento().getDuracao();
            LocalTime inicio = a.getDataHora().toLocalTime();
            LocalTime termino = inicio.plusHours(duracao.getHour()).plusMinutes(duracao.getMinute());
            
            var result = Duration.between(termino, inicioNovoAgendamento).toMinutes();
            
            if (result < INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS) 
                throw new IllegalArgumentException("Não atende ao intervalo mínimo de " + INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS + "min entre cada agendamento");
        });
    }
}