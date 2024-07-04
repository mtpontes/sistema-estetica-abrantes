package br.com.karol.sistema.dto.agendamento;

import java.time.LocalDateTime;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.dto.cliente.IdNomeEmailClienteDTO;
import br.com.karol.sistema.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.dto.usuario.DadosUsuarioDTO;
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
    private DadosProcedimentoDTO procedimento;
    private String observacao;
    private IdNomeEmailClienteDTO cliente;
    private LocalDateTime dataHora;
    private LocalDateTime dataCriacao;
    private DadosUsuarioDTO usuario;

    public DadosAgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.procedimento = new DadosProcedimentoDTO(agendamento.getProcedimento());
        this.observacao = agendamento.getObservacao();
        this.cliente = new IdNomeEmailClienteDTO(agendamento.getCliente());
        this.dataHora = agendamento.getDataHora();
        this.dataCriacao = agendamento.getDataCriacao();
        this.usuario = new DadosUsuarioDTO(agendamento.getUsuario());
    }
}