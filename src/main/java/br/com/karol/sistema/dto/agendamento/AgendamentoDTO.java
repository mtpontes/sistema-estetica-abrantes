package br.com.karol.sistema.dto.agendamento;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.ClienteDTO;
import br.com.karol.sistema.dto.ProcedimentoDTO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {

    // mensagem padrão do Bean Validation é algo como "Cannot be blank" ou "Não pode estar em branco"
    @NotBlank( message = "Campo obrigatório!") 
    private ProcedimentoDTO nomeProcedimento;

    private ClienteDTO cliente;
    
    @NotNull // o @NotBlank é exclusivo para Strings
    @Future //Evita que agendem atendimento com datas passadas
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime dataHora;
    
    private Usuario usuario;


    public AgendamentoDTO(Agendamento agendamento) {
        BeanUtils.copyProperties(agendamento, this);
    }
}
