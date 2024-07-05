package br.com.karol.sistema.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karol.sistema.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.service.ClienteService;
import jakarta.validation.Valid;


@RequestMapping("/clientes")
@RestController
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<DadosCompletosClienteDTO> salvarCliente(@RequestBody @Valid CriarClienteDTO cliente) {
        DadosCompletosClienteDTO clienteSalvo = service.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<DadosClienteDTO>> listaTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodosClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosClienteDTO> buscarCliente(@PathVariable String id) {
        DadosCompletosClienteDTO dto = service.buscarClientePorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosCompletosClienteDTO> editar(@PathVariable String id, @RequestBody AtualizarClienteDTO cliente) {
        return ResponseEntity.ok(service.editar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluirCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}