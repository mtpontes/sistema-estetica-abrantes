package br.com.karol.sistema.unit.utils;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;

public class ClienteUtils {

    public static Cliente getCliente() {
        Cliente cliente = new Cliente();

        Cpf cpf = getCpf("umCpf");
        Telefone telefone = getTelefone("umTelefone");
        Email email = getEmail("umEmail");
        Endereco endereco = new Endereco();
        
        ReflectionTestUtils.setField(cliente, "nome", "umNome");
        ReflectionTestUtils.setField(cliente, "cpf", cpf);
        ReflectionTestUtils.setField(cliente, "telefone", telefone);
        ReflectionTestUtils.setField(cliente, "email", email);
        ReflectionTestUtils.setField(cliente, "endereco", endereco);

        return cliente;
    }
    

    private static Cpf getCpf(String value) {
        Cpf cpf = new Cpf();
        ReflectionTestUtils.setField(cpf, "value", value);
        return cpf;
    }
    
    private static Telefone getTelefone(String value) {
        Telefone telefone = new Telefone();
        ReflectionTestUtils.setField(telefone, "value", value);
        return telefone;
    }

    private static Email getEmail(String value) {
        Email email = new Email();
        ReflectionTestUtils.setField(email, "value", value);
        return email;
    }
}