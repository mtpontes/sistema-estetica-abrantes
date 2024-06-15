package br.com.karol.sistema.dto;

import br.com.karol.sistema.domain.Procedimento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data

public class ProcedimentoDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Double valor;

    public ProcedimentoDTO(Procedimento procedimento) {
        BeanUtils.copyProperties(procedimento, this);
    }


}
