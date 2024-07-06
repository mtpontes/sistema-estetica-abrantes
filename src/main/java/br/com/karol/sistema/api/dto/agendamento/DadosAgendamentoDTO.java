package br.com.karol.sistema.api.dto.agendamento;

import java.time.LocalDateTime;

import br.com.karol.sistema.api.dto.cliente.DadosContatoClienteDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.domain.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DadosAgendamentoDTO {

    private String id;
    private String observacao;
    private LocalDateTime dataHora;
    private DadosProcedimentoDTO procedimento;
    private DadosContatoClienteDTO cliente;
    private DadosUsuarioDTO usuario;
    private LocalDateTime dataCriacao;

    public DadosAgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.procedimento = new DadosProcedimentoDTO(agendamento.getProcedimento());
        this.observacao = agendamento.getObservacao();
        this.cliente = new DadosContatoClienteDTO(agendamento.getCliente());
        this.dataHora = agendamento.getDataHora();
        this.dataCriacao = agendamento.getDataCriacao();
        this.usuario = new DadosUsuarioDTO(agendamento.getUsuario());
    }
}