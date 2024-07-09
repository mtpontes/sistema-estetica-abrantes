package br.com.karol.sistema.api.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.cliente.AtualizarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosAtualizacaoDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosContatoClienteDTO;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClienteMapper {

    private final CpfMapper cpfMapper;
    private final TelefoneMapper telefoneMapper;
    private final EmailMapper emailMapper;
    private final EnderecoMapper enderecoMapper;

    
    public Cliente toCliente(CriarClienteDTO dados) {
        Cpf cpf = this.cpfMapper.toCpf(dados.getCpf());
        Telefone telefone = this.telefoneMapper.toTelefone(dados.getTelefone());
        Email email = this.emailMapper.toEmail(dados.getEmail());
        Endereco endereco = enderecoMapper.toEndereco(dados.getEndereco());

        return new Cliente(dados.getNome(), cpf, telefone, email, endereco);
    }
    public DadosAtualizacaoDTO toDadosAtualizacaoDTO(AtualizarClienteDTO dados) {
        Telefone telefone = this.telefoneMapper.toTelefoneOrNull(dados.getTelefone());
        Email email = this.emailMapper.toEmailOrNull(dados.getEmail());
        return new DadosAtualizacaoDTO(dados.getNome(), telefone, email);
    }

    public DadosCompletosClienteDTO toDadosCompletosClienteDTO(Cliente cliente) {
        return new DadosCompletosClienteDTO(cliente);
    }
    public DadosClienteDTO toDadosClienteDTO(Cliente cliente) {
        return new DadosClienteDTO(cliente);
    }

    public List<DadosClienteDTO> toListDadosClienteDTO(List<Cliente> clienteList) {
        return clienteList.stream().map(c -> this.toDadosClienteDTO(c)).collect(Collectors.toList());
    }

    public DadosContatoClienteDTO toIdNomeEmailClienteDTO(Cliente cliente) {
        return new DadosContatoClienteDTO(cliente);
    }
}