package br.com.karol.sistema.service;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.repository.ClienteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class ClienteService {

    private final ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Cliente> salvar(Cliente cliente) {
        Cliente cliente1 = repository.save(cliente);
        return ResponseEntity.ok(cliente1);
    }

    public ResponseEntity<Void> excluir(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = repository.findAll();
        return ResponseEntity.ok(clientes);
    }

    public ResponseEntity<Cliente> buscar(Integer id) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    public ResponseEntity<Cliente> editar(Integer id, Cliente clienteAtualizado) {
        if (repository.existsById(id)) {
            clienteAtualizado.setId(id);
            Cliente clienteSalvo = repository.save(clienteAtualizado);
            return ResponseEntity.ok(clienteSalvo);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }
}
