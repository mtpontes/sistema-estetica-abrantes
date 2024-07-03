package br.com.karol.sistema.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.dto.cliente.IdNomeEmailClienteDTO;

@Component
public class ClienteMapper {

    private ModelMapper mapper;

    public ClienteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    
    public Cliente toCliente(CriarClienteDTO dados) {
        return new Cliente(dados.getCpf(), dados.getNome(), dados.getTelefone(), dados.getEmail(), mapper.map(dados.getEndereco(), Endereco.class));
    }

    public DadosCompletosClienteDTO toDadosCompletosClienteDTO(Cliente cliente) {
        return mapper.map(cliente, DadosCompletosClienteDTO.class);
    }
    public DadosClienteDTO toDadosClienteDTO(Cliente cliente) {
        return mapper.map(cliente, DadosClienteDTO.class);
    }

    public List<DadosClienteDTO> toListDadosClienteDTO(List<Cliente> clienteList) {
        return clienteList.stream().map(c -> toDadosClienteDTO(c)).collect(Collectors.toList());
    }

    public IdNomeEmailClienteDTO toIdNomeEmailClienteDTO(Cliente cliente) {
        return mapper.map(cliente, IdNomeEmailClienteDTO.class);
    }
}