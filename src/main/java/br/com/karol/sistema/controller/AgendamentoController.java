package br.com.karol.sistema.controller;


import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.dto.AgendamentoDTO;
import br.com.karol.sistema.mapper.AgendamentoMapper;
import br.com.karol.sistema.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
@Data
public class AgendamentoController {
    private final AgendamentoService service;
    private final AgendamentoMapper mapper;

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamento() {
        List<Agendamento> agendamentos = service.listarTodos();
        List<AgendamentoDTO> agendados = mapper.agendamentoListToAgendamentoDTOList(agendamentos);
        return ResponseEntity.status(HttpStatus.OK).body(agendados);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Agendamento> optional = service.buscarPorId(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AgendamentoDTO agendamentoDTO = mapper.agendamentoToAgendamentoDTO(optional.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoDTO);

    }

    @PostMapping
    public ResponseEntity<AgendamentoDTO> salvar(@Valid @RequestBody Agendamento agendamento) throws Exception {
        Agendamento agendar = service.salvar(agendamento);
        AgendamentoDTO agendamentoDTO = mapper.agendamentoToAgendamentoDTO(agendar);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoDTO);


    }

//    @PutMapping
//    public ResponseEntity<AgendamentoDTO> atualizar(@PathVariable Integer id, @RequestBody Agendamento agendamento) {
//        Optional<Agendamento> optional = service.buscarPorId(agendamento.getId());
//        if (optional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//
//
//
//        }
//
//    }

//    @DeleteMapping
//    public ResponseEntity<AgendamentoDTO> deletar(@RequestBody Agendamento agendamento) {
//
//    }
}
