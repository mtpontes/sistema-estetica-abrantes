package br.com.karol.sistema.api.factory;


import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientFactory {

    private final CpfFactory cpfFactory;
    private final TelefoneFactory telefoneFactory;
    private final EmailFactory emailFactory;


    public Cliente criarCliente(
        String nome, 
        String cpfString, 
        String telefoneString, 
        String emailString, 
        Endereco endereco
    ) {
        Cpf cpf = cpfFactory.createCpf(cpfString);
        Telefone telefone = telefoneFactory.createTelefone(telefoneString);
        Email email = emailFactory.createEmail(emailString);
        return new Cliente(nome, cpf, telefone, email, endereco);
    }

    public Cliente criarClienteComUsuario(
        String nome, 
        String cpfString, 
        String telefoneString, 
        String emailString, 
        Endereco endereco,
        Usuario usuario
    ) {
        Cpf cpf = cpfFactory.createCpf(cpfString);
        Telefone telefone = telefoneFactory.createTelefone(telefoneString);
        Email email = emailFactory.createEmail(emailString);
        return new Cliente(nome, cpf, telefone, email, endereco);
    }
}