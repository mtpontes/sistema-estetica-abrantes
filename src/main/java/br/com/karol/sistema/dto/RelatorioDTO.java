package br.com.karol.sistema.dto;

import java.time.LocalDate;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDTO {
    
    private Long codigo;
    private Cliente cliente;
    private Agendamento agendamento;
    private Procedimento procedimento;
    private Usuario responsavelAgendamento;
    private String observacao;
    private LocalDate dataCadastro = LocalDate.now();
}