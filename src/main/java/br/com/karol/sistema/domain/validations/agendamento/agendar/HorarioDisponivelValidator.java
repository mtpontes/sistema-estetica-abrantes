package br.com.karol.sistema.domain.validations.agendamento.agendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;

@Component
public class HorarioDisponivelValidator implements AgendamentoValidator {

    private static final Integer INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS = 29;
    private AgendamentoRepository repository;

    public HorarioDisponivelValidator(AgendamentoRepository repository) {
        this.repository = repository;
    }


    @Override
    public void validate(Agendamento dados) {
        LocalTime inicioNovoAgendamento = dados.getDataHora().toLocalTime();
        LocalDate dataNovoAgendamento = dados.getDataHora().toLocalDate();
        
        List<Agendamento> agendamentos = repository.findByAgendamentosBetweenDataHora(
            dataNovoAgendamento.atStartOfDay(), 
            dataNovoAgendamento.atTime(23, 59, 59));

        agendamentos.forEach(agendamento -> {
            if (agendamento.getId() != null && agendamento.getId().equals(dados.getId())) return;
            LocalTime inicio = agendamento.getDataHora().toLocalTime();
            LocalTime duracao = agendamento.getProcedimento().getDuracao();
            LocalTime termino = inicio.plusHours(duracao.getHour()).plusMinutes(duracao.getMinute());
            
            // Verifica se o novo agendamento começa antes do término do agendamento atual, incluindo o intervalo de 29 minutos
            if (!inicioNovoAgendamento.isAfter(termino.plusMinutes(INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS))) {
                throw new IllegalArgumentException("Não atende ao intervalo mínimo de " + INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS + "min entre cada agendamento");
            }
        });
    }
}