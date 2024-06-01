package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.repository.ClienteRepository;
import br.com.karol.sistema.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/cliente")
@RestController
public class ClienteController {
    private final ClienteService service;



    public ClienteController(ClienteService service) {
        this.service = service;

    }

    @PostMapping("/salvarCliente")
    public ResponseEntity<Cliente> inserirCliente(@RequestBody Cliente cliente) {

        return ResponseEntity.ok(service.salvar(cliente).getBody());

    }

    @GetMapping("/listarCliente")
    public ResponseEntity<List<Cliente>> listarCliente() {
        return ResponseEntity.ok(service.listar().getBody());
    }

    @DeleteMapping("/deletarCliente")
    public ResponseEntity<Void> excluir(@RequestBody int id) {
        return service.excluir(id);
    }

    @PutMapping("/editarCliente")
    public ResponseEntity<Cliente> editar(@RequestBody Integer id, Cliente cliente) {
        return ResponseEntity.ok(service.editar(id, cliente).getBody());
    }

    @GetMapping("/buscarCliente")
    public ResponseEntity<Cliente> buscarCliente(@RequestParam int id) {
        return ResponseEntity.ok(service.buscar(id).getBody());

    }


}
