package br.com.karol.sistema.api.mapper;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;

@Component
public class AgendamentoMapper {

    private final ModelMapper mapper;

    public AgendamentoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public DadosAgendamentoDTO toDadosAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, DadosAgendamentoDTO.class);
    }
    
    public Page<DadosAgendamentoDTO> toPageDadosAgentamentoDTO(Page<Agendamento> agendamentoList) {
        return agendamentoList.map(this::toDadosAgendamentoDTO);
    }

    public Agendamento forAgendamentoValidator(String clienteId, LocalDateTime dataHora, String agendamentoId) {
        Cliente cliente = new Cliente(clienteId, null, null, null, null, null);
        return new Agendamento(agendamentoId, null, null, dataHora, null, cliente, null, null);
    }
}