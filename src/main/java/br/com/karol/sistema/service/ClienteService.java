package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.mapper.ClienteMapper;
import br.com.karol.sistema.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ClienteService {

    private final String NOT_FOUND_MESSAGE = "Cliente não encontrado";
    
    private ClienteRepository repository;
    private ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public DadosCompletosClienteDTO salvarCliente(CriarClienteDTO dadosCriacaoCliente) {
        Cliente cliente = mapper.toCliente(dadosCriacaoCliente);

        Cliente savedCliente = repository.save(cliente);
        return mapper.toDadosCompletosClienteDTO(savedCliente);
    }

    public void excluirCliente(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);

        repository.deleteById(id);
    }

    public List<DadosClienteDTO> listarTodosClientes() {
        return mapper.toListDadosClienteDTO(repository.findAll());
    }

    public DadosCompletosClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = this.buscarPorId(id);
        return mapper.toDadosCompletosClienteDTO(cliente);
    }

    public DadosCompletosClienteDTO editar(Long clienteId, AtualizarClienteDTO dadosAtualizacao) {
        Cliente cliente = this.buscarPorId(clienteId);
        cliente.atualizarDados(dadosAtualizacao.getNome(), dadosAtualizacao.getTelefone(), dadosAtualizacao.getEmail(), dadosAtualizacao.getEndereco());
        return mapper.toDadosCompletosClienteDTO(repository.save(cliente));
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}