package br.com.karol.sistema.business.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.procedimento.AtualizarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.CriarProcedimentoDTO;
import br.com.karol.sistema.api.dto.procedimento.DadosProcedimentoDTO;
import br.com.karol.sistema.api.mapper.ProcedimentoMapper;
import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.AgendamentoRepository;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcedimentoService {

    private static final String NOT_FOUND_MESSAGE = "Procedimento não encontrado";

    private final ProcedimentoRepository repository;
    private final ProcedimentoMapper mapper;
    private final AgendamentoRepository agendamentoRepository;


    @Transactional
    public DadosProcedimentoDTO salvarProcedimento(CriarProcedimentoDTO dados) {
        if (repository.existsByNome(dados.getNome()))
            throw new FieldValidationException("nome");

        Procedimento procedimento = repository.save(mapper.toProcedimento(dados));
        return mapper.toDadosProcedimentoDTO(procedimento);
    }

    public Page<DadosProcedimentoDTO> listarTodosProcedimentos(
        String nomeProcedimento,
        Double valorMin,
        Double valorMax,
        Pageable pageable
    ) {
        return mapper.toPageDadosProcedimentoDTO(repository.findAllByParams(
            nomeProcedimento, 
            valorMin, 
            valorMax, 
            pageable));
    }

    public DadosProcedimentoDTO mostrarProcedimento(Long procedimentoId) {
        return this.mapper.toDadosProcedimentoDTO(this.getProcedimentoById(procedimentoId));
    }

    @Transactional
    public DadosProcedimentoDTO editarProcedimento(
        Long procedimentoId, 
        AtualizarProcedimentoDTO update
    ) {
        if (repository.existsByNome(update.getNome()))
            throw new FieldValidationException("nome");

        Procedimento alvo = this.getProcedimentoById(procedimentoId);
        alvo.atualizarDados(update.getNome(), update.getDescricao(), update.getDuracao(), update.getValor());
        return mapper.toDadosProcedimentoDTO(repository.save(alvo));
    }

    // Criar uma query que valida se existe algum Agendamento com este procedimento
    @Transactional
    public void removerProcedimento(Long procedimentoId) {
        if (!repository.existsById(procedimentoId))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);

        if (agendamentoRepository.existsByProcedimentoIdAndStatusIn(procedimentoId))
            throw new IllegalArgumentException("Procedimento está vinculado a agendamentos em aberto");

        repository.deleteById(procedimentoId);
    }

    public Procedimento getProcedimentoById(Long procedimentoId) {
        return this.repository.findById(procedimentoId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}