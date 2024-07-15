package br.com.karol.sistema.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
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
    
    public MeDadosAgendamentoDTO toMeDadosAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, MeDadosAgendamentoDTO.class);
    }

    public ObservacaoAtualizadaAgendamentoDTO toObservacaoAtualizadaAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, ObservacaoAtualizadaAgendamentoDTO.class);
    }

    public StatusAtualizadoAgendamentoDTO toStatusAtualizadoAgendamentoDTO(Agendamento agendamento) {
        return mapper.map(agendamento, StatusAtualizadoAgendamentoDTO.class);
    }
    
    public Page<DadosAgendamentoDTO> toPageDadosAgentamentoDTO(Page<Agendamento> agendamentoPage) {
        return agendamentoPage.map(this::toDadosAgendamentoDTO);
    }

    public Page<DadosBasicosAgendamentoDTO> toPageDadosBasicosAgentamentoDTO(Page<Agendamento> agendamentoList) {
        return agendamentoList.map(DadosBasicosAgendamentoDTO::new);
    }
}