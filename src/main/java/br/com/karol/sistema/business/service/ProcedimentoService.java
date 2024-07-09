package br.com.karol.sistema.business.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.api.mapper.ProcedimentoMapper;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;

@Service
public class ProcedimentoService {

    private final String NOT_FOUND_MESSAGE = "Procedimento não encontrado";

    private final ProcedimentoRepository repository;
    private final ProcedimentoMapper mapper;

    public ProcedimentoService(ProcedimentoRepository repository, ProcedimentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional
    public DadosProcedimentoDTO salvarProcedimento(CriarProcedimentoDTO dados) {
        Procedimento procedimento = repository.save(mapper.toProcedimento(dados));
        return mapper.toDadosProcedimentoDTO(procedimento);
    }

    public List<DadosProcedimentoDTO> listarTodosProcedimentos() {
        return mapper.toListDadosProcedimentoDTO(repository.findAll());
    }

    public DadosProcedimentoDTO mostrarProcedimento(String procedimentoId) {
        return this.mapper.toDadosProcedimentoDTO(this.getProcedimentoById(procedimentoId));
    }

    @Transactional
    public DadosProcedimentoDTO editarProcedimento(String procedimentoId, AtualizarProcedimentoDTO update){
        Procedimento alvo = this.getProcedimentoById(procedimentoId);
        alvo.atualizarDados(update.getNome(), update.getDescricao(), update.getDuracao(), update.getValor());
        return mapper.toDadosProcedimentoDTO(repository.save(alvo));
    }

    // Criar uma query que valida se existe algum Agendamento com este procedimento
    @Transactional
    public void removerProcedimento(String id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
            
        repository.deleteById(id);
    }

    public Procedimento getProcedimentoById(String id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}