package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDTO {
    private int codigo;
    private Cliente cliente;
    private Agendamento agendamento;
    private Procedimento procedimento;
    private Usuario responsavelAgendamento;
    private String observacao;
    private LocalDate dataCadastro=LocalDate.now();

}
