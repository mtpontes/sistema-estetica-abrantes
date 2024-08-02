package br.com.karol.sistema.business.validators;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.constants.AgendamentoConstants;
import br.com.karol.sistema.domain.validator.AgendamentoValidator;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class HorarioDisponivelValidator implements AgendamentoValidator {
    
    private static final Integer INTERVALO = AgendamentoConstants.INTERVALO_ENTRE_AGENDAMENTOS_EM_MINUTOS;

    private final AgendamentoRepository repository;


    @Override
    public void validate(Agendamento dados) {
        LocalTime inicioNovoAgendamento = dados.getDataHora().toLocalTime();
        LocalDate dataNovoAgendamento = dados.getDataHora().toLocalDate();
        
        List<Agendamento> agendamentos = repository.findByDataHoraBetween(
            dataNovoAgendamento.atStartOfDay(), 
            dataNovoAgendamento.atTime(23, 59, 59));

        agendamentos.forEach(agendamento -> {
            if (agendamento.getId() != null && agendamento.getId().equals(dados.getId())) return;
            LocalTime inicio = agendamento.getDataHora().toLocalTime();
            LocalTime duracao = agendamento.getProcedimento().getDuracao();
            LocalTime termino = inicio.plusHours(duracao.getHour()).plusMinutes(duracao.getMinute());
            
            // Verifica se o novo agendamento começa antes do término do agendamento atual, incluindo o INTERVALO
            if (!inicioNovoAgendamento.isAfter(termino.plusMinutes(INTERVALO))) {
                throw new IllegalArgumentException("Não atende ao intervalo mínimo de " + 
                INTERVALO + "min entre cada agendamento");
            }
        });
    }
}