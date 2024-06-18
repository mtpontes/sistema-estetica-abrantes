package br.com.karol.sistema.controller;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.dto.ClienteDTO;
import br.com.karol.sistema.mapper.ClienteMapper;
import br.com.karol.sistema.service.ClienteService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/cliente")
@RestController
@Data
public class ClienteController {
    private final ClienteService service;
    private final ClienteMapper mapper;


    @PostMapping
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    public ResponseEntity<ClienteDTO> inserirCliente(@Valid @RequestBody Cliente cliente) {
        ClienteDTO clienteSalvo = service.savarCliente(cliente);
        //transforma o cliente em dto
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);

    }

    public static void main(String[] args) {

    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = service.listar();
//        ClienteDTO dto=mapper.clienteToClienteDTO(clientes);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Integer id) {
        ClienteDTO dto=service.getCliente(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        service.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<Cliente> editar(@RequestBody Integer id, Cliente cliente) {
        return ResponseEntity.ok(service.editar(id, cliente));
    }


}
