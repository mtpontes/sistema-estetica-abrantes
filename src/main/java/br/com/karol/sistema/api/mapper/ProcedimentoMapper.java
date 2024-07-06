package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.domain.Procedimento;

@Component
public class ProcedimentoMapper {

    public Procedimento toProcedimento(CriarProcedimentoDTO dto) {
        return new Procedimento(dto.getNome(), dto.getDescricao(), dto.getDuracao(), dto.getValor());
    }

    public DadosProcedimentoDTO toDadosProcedimentoDTO(Procedimento dados) {
        return new DadosProcedimentoDTO(dados);
    }
    
    public List<DadosProcedimentoDTO> toListDadosProcedimentoDTO(List<Procedimento> dados) {
        return dados.stream()
            .map(this::toDadosProcedimentoDTO)
            .toList();
    }
}