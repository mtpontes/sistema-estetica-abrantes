package br.com.karol.sistema.service;


import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.repository.ProcedimentoRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Data

public class ProcedimentoService {
    private final ProcedimentoRepository repository;

    public ProcedimentoService(ProcedimentoRepository repository) {
        this.repository = repository;
    }


    public ResponseEntity<Procedimento> salvar(Procedimento procedimento) {
        Procedimento procedimento1 = repository.save(procedimento);
        return ResponseEntity.ok().body(procedimento1);
    }

    public List<Procedimento> listar() {
        repository.findAll();


        return List.of();
    }
    public ResponseEntity<Procedimento>remover(Procedimento procedimento){
        repository.delete(procedimento);
        return ResponseEntity.ok().body(procedimento);

    }
    public ResponseEntity<Procedimento>atualizar(Procedimento procedimento){
        repository.save(procedimento);
        return ResponseEntity.ok().body(procedimento);
    }


}
