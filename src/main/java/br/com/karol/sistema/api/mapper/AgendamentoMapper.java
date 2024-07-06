package br.com.karol.sistema.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.domain.Agendamento;

@Component
public class AgendamentoMapper {

    private final ModelMapper mapper;

    public AgendamentoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public DadosAgendamentoDTO toDadosAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, DadosAgendamentoDTO.class);
    }
    
    public List<DadosAgendamentoDTO> toListDadosAgentamentoDTO(List<Agendamento> agendamentoList) {
        return agendamentoList.stream().map(this::toDadosAgendamentoDTO).collect(Collectors.toList());
    }
}