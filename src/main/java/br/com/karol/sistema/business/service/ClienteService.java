package br.com.karol.sistema.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosAtualizacaoDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.api.mapper.ClienteMapper;
import br.com.karol.sistema.api.mapper.EnderecoMapper;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {

    private static final String NOT_FOUND_MESSAGE = "Cliente não encontrado";
    
    private ClienteRepository repository;
    private ClienteMapper mapper;
    private EnderecoMapper enderecoMapper;


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
    public DadosCompletosClienteDTO editarContatoCliente(String clienteId, AtualizarClienteDTO dados) {
        Cliente alvo = this.buscarPorId(clienteId);
        DadosAtualizacaoDTO dadosAtualizacao = mapper.toDadosAtualizacaoDTO(dados);
        alvo.atualizarDados(dadosAtualizacao.getNome(), dadosAtualizacao.getTelefone(), dadosAtualizacao.getEmail());

        return mapper.toDadosCompletosClienteDTO(repository.save(alvo));
    }

    @Transactional
    public DadosCompletosClienteDTO editarEnderecoCliente(String clienteId, EnderecoDTO dadosAtualizacao) {
        System.out.println("DTO: " + dadosAtualizacao);
        Cliente alvo = this.buscarPorId(clienteId);
        Endereco novoEndereco = enderecoMapper.toEndereco(dadosAtualizacao);
        alvo.atualizarEndereco(novoEndereco);

        return mapper.toDadosCompletosClienteDTO(repository.save(alvo));
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