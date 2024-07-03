package br.com.karol.sistema.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.mapper.ProcedimentoMapper;
import br.com.karol.sistema.repository.ProcedimentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ProcedimentoService {

    private ProcedimentoRepository repository;
    private ProcedimentoMapper mapper;

    public ProcedimentoService(ProcedimentoRepository repository, ProcedimentoMapper mapper) {
        this.repository = repository;
    }


    public DadosProcedimentoDTO salvar(CriarProcedimentoDTO dados) {
        Procedimento procedimento = repository.save(mapper.toProcedimento(dados));
        return mapper.toDadosProcedimentoDTO(procedimento);
    }

    public List<DadosProcedimentoDTO> listar() {
        return mapper.toListDadosProcedimentoDTO(repository.findAll());
    }

    public void remover(Long id){
        repository.deleteById(id);
    }

    public DadosProcedimentoDTO atualizar(Long procedimentoId, AtualizarProcedimentoDTO update){
        Procedimento alvo = this.getProcedimentoById(procedimentoId);
        alvo.atualizarDados(update.getNome(), update.getDescricao(), update.getValor());
        return mapper.toDadosProcedimentoDTO(repository.save(alvo));
    }

    private Procedimento getProcedimentoById(Long id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado"));
    }
}