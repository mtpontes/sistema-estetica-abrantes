package br.com.karol.sistema.api.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.cliente.CriarClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosCompletosClienteDTO;
import br.com.karol.sistema.api.dto.cliente.DadosContatoClienteDTO;
import br.com.karol.sistema.domain.Cliente;

@Component
public class ClienteMapper {

    private ModelMapper mapper;

    public ClienteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    
    public Cliente toCliente(CriarClienteDTO dados) {
        return new Cliente(dados.getCpf(), dados.getNome(), dados.getTelefone(), dados.getEmail(), dados.getEndereco());
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

    public DadosContatoClienteDTO toIdNomeEmailClienteDTO(Cliente cliente) {
        return mapper.map(cliente, DadosContatoClienteDTO.class);
    }
}