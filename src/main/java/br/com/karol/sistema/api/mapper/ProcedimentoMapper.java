package br.com.karol.sistema.api.mapper;

import org.springframework.data.domain.Page;
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

    public Page<DadosProcedimentoDTO> toPageDadosProcedimentoDTO(Page<Procedimento> dados) {
        return dados.map(this::toDadosProcedimentoDTO);
    }
}