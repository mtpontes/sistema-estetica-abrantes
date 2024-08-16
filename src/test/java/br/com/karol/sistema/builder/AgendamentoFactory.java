package br.com.karol.sistema.builder;

import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.enums.StatusAgendamento;

public class AgendamentoFactory {

    public static Agendamento getAgendamento() {
        Agendamento agendamento = new Agendamento();

        Procedimento procedimento = ProcedimentoFactory.getProcedimento();
        Cliente cliente = ClienteFactory.getCliente();
        
        ReflectionTestUtils.setField(agendamento, "id", 1L);
        ReflectionTestUtils.setField(agendamento, "observacao", "observacao utils");
        ReflectionTestUtils.setField(agendamento, "status", StatusAgendamento.PENDENTE);
        ReflectionTestUtils.setField(agendamento, "dataHora", LocalDateTime.now().plusDays(1).withNano(0));
        ReflectionTestUtils.setField(agendamento, "procedimento", procedimento);
        ReflectionTestUtils.setField(agendamento, "cliente", cliente);
        ReflectionTestUtils.setField(agendamento, "usuarioLogin", "any login");
        var now = LocalDateTime.now();
        ReflectionTestUtils.setField(agendamento, "dataCriacao", now);
        ReflectionTestUtils.setField(agendamento, "dataModificacao", now);

        return agendamento;
    }
}