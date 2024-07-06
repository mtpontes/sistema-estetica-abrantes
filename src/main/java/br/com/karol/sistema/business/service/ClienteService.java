package br.com.karol.sistema.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.api.mapper.ClienteMapper;
import br.com.karol.sistema.api.mapper.EnderecoMapper;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {

    private final String NOT_FOUND_MESSAGE = "Cliente não encontrado";
    
    private ClienteRepository repository;
    private ClienteMapper mapper;
    private EnderecoMapper enderecoMapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper, EnderecoMapper enderecoMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this. enderecoMapper = enderecoMapper;
    }


    @Transactional
    public DadosCompletosClienteDTO salvarCliente(CriarClienteDTO dadosCriacaoCliente) {
        Cliente cliente = mapper.toCliente(dadosCriacaoCliente);

        Cliente savedCliente = repository.save(cliente);
        return mapper.toDadosCompletosClienteDTO(savedCliente);
    }

    public List<DadosClienteDTO> listarTodosClientes() {
        return mapper.toListDadosClienteDTO(repository.findAll());
    }

    public DadosCompletosClienteDTO buscarClientePorId(String id) {
        Cliente cliente = this.buscarPorId(id);
        return mapper.toDadosCompletosClienteDTO(cliente);
    }

    @Transactional
    public DadosCompletosClienteDTO editarCliente(String clienteId, AtualizarClienteDTO dadosAtualizacao) {
        Cliente cliente = this.buscarPorId(clienteId);
        cliente.atualizarDados(dadosAtualizacao.getNome(), dadosAtualizacao.getTelefone(), dadosAtualizacao.getEmail(), enderecoMapper.toEndereco(dadosAtualizacao.getEndereco()));
        return mapper.toDadosCompletosClienteDTO(repository.save(cliente));
    }

    @Transactional
    public void removerCliente(String id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);

        repository.deleteById(id);
    }

    public Cliente buscarPorId(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}