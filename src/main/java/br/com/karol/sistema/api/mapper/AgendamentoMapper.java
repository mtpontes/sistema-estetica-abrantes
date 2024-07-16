package br.com.karol.sistema.api.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.agendamento.DadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.DadosBasicosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.MeDadosAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.ObservacaoAtualizadaAgendamentoDTO;
import br.com.karol.sistema.api.dto.agendamento.StatusAtualizadoAgendamentoDTO;
import br.com.karol.sistema.domain.Agendamento;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AgendamentoMapper {

    public DadosAgendamentoDTO toDadosAgendamentoDTO(Agendamento agendamento) {
        return new DadosAgendamentoDTO(agendamento);
    }
    
    public MeDadosAgendamentoDTO toMeDadosAgendamentoDTO(Agendamento agendamento) {
        return new MeDadosAgendamentoDTO(agendamento);
    }

    public ObservacaoAtualizadaAgendamentoDTO toObservacaoAtualizadaAgendamentoDTO(Agendamento agendamento) {
        return new ObservacaoAtualizadaAgendamentoDTO(agendamento);
    }

    public StatusAtualizadoAgendamentoDTO toStatusAtualizadoAgendamentoDTO(Agendamento agendamento) {
        return new StatusAtualizadoAgendamentoDTO(agendamento);
    }
    
    public Page<DadosAgendamentoDTO> toPageDadosAgentamentoDTO(Page<Agendamento> agendamentoPage) {
        return agendamentoPage.map(this::toDadosAgendamentoDTO);
    }

    public Page<DadosBasicosAgendamentoDTO> toPageDadosBasicosAgentamentoDTO(Page<Agendamento> agendamentoList) {
        return agendamentoList.map(DadosBasicosAgendamentoDTO::new);
    }
}