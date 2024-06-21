package br.com.karol.sistema.dto.agendamento;

import java.time.LocalDateTime;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.dto.ProcedimentoDTO;
import br.com.karol.sistema.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.dto.usuario.DadosUsuarioDTO;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosAgendamentoDTO {

    private Integer id;
    private ProcedimentoDTO tipoProcedimento;
    private String observacao;
    private DadosClienteDTO cliente;
    private LocalDateTime dataHora;
    private LocalDateTime dataCriacao;
    private DadosUsuarioDTO usuario;

    public DadosAgendamentoDTO(Agendamento agendament) {
        this.id = agendament.getId();
        this.tipoProcedimento = new ProcedimentoDTO(agendament.getTipoProcedimento());
        this.observacao = agendament.getObservacao();
        this.cliente = new DadosClienteDTO(agendament.getCliente());
        this.dataHora = agendament.getDataHora();
        this.dataCriacao = agendament.getDataCriacao();
        this.usuario = new DadosUsuarioDTO(agendament.getUsuario());
    }
}