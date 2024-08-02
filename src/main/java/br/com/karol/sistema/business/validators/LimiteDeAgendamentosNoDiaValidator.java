package br.com.karol.sistema.business.validators;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.constants.AgendamentoConstants;
import br.com.karol.sistema.domain.validator.AgendamentoValidator;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LimiteDeAgendamentosNoDiaValidator implements AgendamentoValidator {
    
    private static final Integer LIMITE = AgendamentoConstants.LIMITE_AGENDAMENTOS_NO_DIA;

    private final AgendamentoRepository repository;


    @Override
    public void validate(Agendamento dados) {
        LocalDate dataAgendamento = dados.getDataHora().toLocalDate();


        Long result = repository.countByClienteIdAndDataHoraBetween(
            dados.getCliente().getId(),
            dataAgendamento.atStartOfDay(),
            dataAgendamento.atTime(23, 59, 59));

        if (result != null && result >= LIMITE)
            throw new IllegalArgumentException("Cliente atingiu o limite de agendamentos no dia");
    }
}