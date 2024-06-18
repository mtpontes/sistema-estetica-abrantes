package br.com.karol.sistema.controller;


import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.dto.AgendamentoDTO;
import br.com.karol.sistema.mapper.AgendamentoMapper;
import br.com.karol.sistema.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;
    @Autowired
    private AgendamentoMapper mapper;


    @GetMapping("/listarAgendamentos")
    public List<AgendamentoDTO> listarAgendamento() {
        List<Agendamento> agendamentos = service.listarTodos();
        return mapper.agendamentoListToAgendamentoDTOList(agendamentos);
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

    @PostMapping("/criar")
    public ResponseEntity<AgendamentoDTO> salvar(@Valid @RequestBody Agendamento agendamento, Authentication authentication) throws Exception {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        agendamento.setUsuario(usuario);
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
