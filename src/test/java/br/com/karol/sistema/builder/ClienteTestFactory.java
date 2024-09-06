package br.com.karol.sistema.builder;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.utils.ClienteUtils;

public class ClienteTestFactory {

    public static Cliente getCliente() {
        return new ClienteTestBuilder()
            .id(1L)
            .nome("default name")
            .cpf("12345678911")
            .telefone("12345678911")
            .email("default email")
            .endereco(ClienteUtils.getEndereco())
            .build();
    }
}