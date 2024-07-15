package br.com.karol.sistema.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.CriarUsuarioClienteDTO;
import br.com.karol.sistema.api.dto.EnderecoDTO;
import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosAtualizacaoDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.mapper.ClienteMapper;
import br.com.karol.sistema.api.mapper.EnderecoMapper;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {

    private static final String NOT_FOUND_MESSAGE = "Cliente não encontrado";
    
    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final EnderecoMapper enderecoMapper;
    private final UsuarioService usuarioService;


    @Transactional
    public DadosCompletosClienteDTO salvarCliente(CriarClienteDTO dadosCriacaoCliente) {
        Cliente cliente = mapper.toCliente(dadosCriacaoCliente);
        Cliente savedCliente = repository.save(cliente);
        return mapper.toDadosCompletosClienteDTO(savedCliente);
    }
    @Transactional
    public DadosCompletosClienteDTO salvarClienteComUsuario (CriarUsuarioClienteDTO dados) {
        Usuario usuario = this.usuarioService.salvarUsuarioCliente(
            new CriarUsuarioDTO(dados.getNome(), dados.getLogin(), dados.getSenha()));
        Cliente cliente = mapper.toCliente(dados, usuario);

        Cliente savedCliente = repository.save(cliente);
        return mapper.toDadosCompletosClienteDTO(savedCliente);
    }

    public Page<DadosClienteDTO> listarTodosClientes(String nome, Pageable pageable) {
        return mapper.toPageDadosClienteDTO(repository.findAllByParams(nome, pageable));
    }

    public DadosCompletosClienteDTO buscarClientePorId(Long clienteId) {
        Cliente cliente = this.buscarPorId(clienteId);
        return mapper.toDadosCompletosClienteDTO(cliente);
    }

    @Transactional
    public DadosCompletosClienteDTO editarContatoCliente(Long clienteId, AtualizarClienteDTO dados) {
        Cliente alvo = this.buscarPorId(clienteId);
        DadosAtualizacaoDTO dadosAtualizacao = mapper.toDadosAtualizacaoDTO(dados);
        alvo.atualizarDados(dadosAtualizacao.getNome(), dadosAtualizacao.getTelefone(), dadosAtualizacao.getEmail());

        return mapper.toDadosCompletosClienteDTO(repository.save(alvo));
    }
    @Transactional
    public DadosCompletosClienteDTO editarContatoCliente(Cliente cliente, AtualizarClienteDTO dados) {
        DadosAtualizacaoDTO dadosAtualizacao = mapper.toDadosAtualizacaoDTO(dados);
        cliente.atualizarDados(dadosAtualizacao.getNome(), dadosAtualizacao.getTelefone(), dadosAtualizacao.getEmail());

        return mapper.toDadosCompletosClienteDTO(repository.save(cliente));
    }

    @Transactional
    public DadosCompletosClienteDTO editarEnderecoCliente(Long clienteId, EnderecoDTO dadosAtualizacao) {
        Cliente alvo = this.buscarPorId(clienteId);
        Endereco novoEndereco = enderecoMapper.toEndereco(dadosAtualizacao);
        alvo.atualizarEndereco(novoEndereco);

        return mapper.toDadosCompletosClienteDTO(repository.save(alvo));
    }
    @Transactional
    public DadosCompletosClienteDTO editarEnderecoCliente(Cliente cliente, EnderecoDTO dadosAtualizacao) {
        Endereco novoEndereco = enderecoMapper.toEndereco(dadosAtualizacao);
        cliente.atualizarEndereco(novoEndereco);

        return mapper.toDadosCompletosClienteDTO(repository.save(cliente));
    }

    @Transactional
    public void removerCliente(Long clienteId) {
        if (!repository.existsById(clienteId))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);

        repository.deleteById(clienteId);
    }

    public Cliente getClienteByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).orElseThrow(EntityNotFoundException::new);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}