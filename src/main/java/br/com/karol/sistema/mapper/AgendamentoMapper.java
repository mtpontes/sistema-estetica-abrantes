package br.com.karol.sistema.mapper;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.dto.agendamento.AgendamentoDTO;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class AgendamentoMapper {

    @Autowired
    private ModelMapper mapper;

    public AgendamentoDTO agendamentoToAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, AgendamentoDTO.class);
    }


    public Agendamento clienteDTOToCliente(AgendamentoDTO agendamentoDTO) {
        return mapper.map(agendamentoDTO, Agendamento.class);
    }

    public List<AgendamentoDTO> agendamentoListToAgendamentoDTOList(List<Agendamento> agendamentoList) {
        return agendamentoList.stream().map(this::agendamentoToAgendamentoDTO).collect(Collectors.toList());
    }
}