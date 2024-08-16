package br.com.karol.sistema.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Agendamento;
import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.domain.Endereco;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.valueobjects.Cpf;
import br.com.karol.sistema.domain.valueobjects.Email;
import br.com.karol.sistema.domain.valueobjects.Telefone;

public class ClienteBuilder {

    private Long id;
    private String nome;
    private Cpf cpf;
    private Telefone telefone;
    private Email email;
    private Endereco endereco;
    private Usuario usuario;
    private Set<Agendamento> agendamentos = new HashSet<>();

    public ClienteBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public ClienteBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public ClienteBuilder cpf(String value) {
        Cpf cpf = new Cpf();
        ReflectionTestUtils.setField(cpf, "value", value);
        this.cpf = cpf;
        return this;
    }
    
    public ClienteBuilder telefone(String value) {
        Telefone telefone = new Telefone();
        ReflectionTestUtils.setField(telefone, "value", value);
        this.telefone = telefone;
        return this;
    }
    
    public ClienteBuilder email(String value) {
        Email email = new Email();
        ReflectionTestUtils.setField(email, "value", value);
        this.email = email;
        return this;
    }

    public ClienteBuilder endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public ClienteBuilder usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public ClienteBuilder agendamentos(List<Agendamento> agendamentos) {
        this.agendamentos.addAll(agendamentos);
        return this;
    }

    public Cliente build() {
        Cliente cliente = new Cliente();

        ReflectionTestUtils.setField(cliente, "id", this.id);
        ReflectionTestUtils.setField(cliente, "nome", this.nome);
        ReflectionTestUtils.setField(cliente, "cpf", this.cpf);
        ReflectionTestUtils.setField(cliente, "telefone", this.telefone);
        ReflectionTestUtils.setField(cliente, "email", this.email);
        ReflectionTestUtils.setField(cliente, "endereco", this.endereco);
        ReflectionTestUtils.setField(cliente, "usuario", this.usuario);
        ReflectionTestUtils.setField(cliente, "agendamentos", this.agendamentos);

        return cliente;
    }
}