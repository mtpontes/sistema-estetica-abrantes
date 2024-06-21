package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.dto.ClienteDTO;
import br.com.karol.sistema.exceptions.ClienteException;
import br.com.karol.sistema.mapper.ClienteMapper;
import br.com.karol.sistema.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Service
@Transactional
@Data
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteMapper mapper;


//    public ClienteDTO salvar(ClienteDTO cliente) {
//        Cliente cliente1 = mapper.clienteDTOToCliente(cliente);
//        return mapper.clienteToClienteDTO(repository.save(cliente1));
//
//
//    }

    @Transactional
    public ClienteDTO savarCliente(Cliente cliente) {
        cliente.getEnderecos().forEach(endereco -> endereco.setCliente(cliente));
        Cliente savedCliente = repository.save(cliente);
        return mapper.clienteToClienteDTO(savedCliente);
    }

    public void excluir(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    public List<Cliente> listar() {
        return repository.findAll();

    }

    public Cliente buscarPorId(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }
    
    @Transactional(readOnly = true)
    public ClienteDTO getCliente(Integer id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteException("Cliente não encontrado"));
        return mapper.clienteToClienteDTO(cliente);
    }

    public Cliente editar(Integer id, Cliente clienteAtualizado) {
        if (repository.existsById(id)) {
            clienteAtualizado.setId(id);
            return repository.save(clienteAtualizado);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }
}