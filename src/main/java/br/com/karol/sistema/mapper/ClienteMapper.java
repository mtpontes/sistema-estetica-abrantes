package br.com.karol.sistema.mapper;


import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.dto.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteMapper {

    private ModelMapper mapper;


    public ClienteDTO clienteToClienteDTO(Cliente cliente) {
        return mapper.map(cliente, ClienteDTO.class);
    }

    public Cliente clienteDTOToCliente(ClienteDTO clienteDTO) {
        return mapper.map(clienteDTO, Cliente.class);
    }

    public List<ClienteDTO> clienteListToClienteDTOList(List<Cliente> clienteList) {
        return clienteList.stream().map(this::clienteToClienteDTO).collect(Collectors.toList());
    }

//    public ClienteDTO convertToDTO(Cliente cliente) {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setId(cliente.getId());
//        clienteDTO.setNome(cliente.getNome());
//        clienteDTO.setEmail(cliente.getEmail());
//        clienteDTO.setTelefone(cliente.getTelefone());
//        clienteDTO.setEnderecos(cliente.getEnderecos()
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList()));
//        return clienteDTO;
//    }
//
//    public EnderecoDTO convertToDTO(Endereco endereco) {
//        EnderecoDTO enderecoDTO = new EnderecoDTO();
//        enderecoDTO.setId(endereco.getId());
//        enderecoDTO.setRua(endereco.getRua());
//        enderecoDTO.setNumero(endereco.getNumero());
//        enderecoDTO.setCidade(endereco.getCidade());
//        enderecoDTO.setEstado(endereco.getEstado());
//        enderecoDTO.setBairro(endereco.getBairro());
//
//        return enderecoDTO;
//    }


}
