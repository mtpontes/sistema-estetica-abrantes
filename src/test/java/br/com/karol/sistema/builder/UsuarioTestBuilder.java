package br.com.karol.sistema.builder;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;

public class UsuarioTestBuilder {

    private Long id;
    private String nome;
    private Login login;
    private Senha senha;
    private UserRole role;

    public UsuarioTestBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public UsuarioTestBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public UsuarioTestBuilder login(String value) {
        Login login = new Login();
        ReflectionTestUtils.setField(login, "value", value);
        this.login = login;
        return this;
    }
    
    public UsuarioTestBuilder senha(String value) {
        Senha senha = new Senha();
        ReflectionTestUtils.setField(senha, "value", value);
        this.senha = senha;
        return this;
    }

    public UsuarioTestBuilder role(UserRole role) {
        this.role = role;
        return this;
    }

    public Usuario build() {
        Usuario user = new Usuario();

        ReflectionTestUtils.setField(user, "id", this.id);
        ReflectionTestUtils.setField(user, "nome", this.nome);
        ReflectionTestUtils.setField(user, "login", this.login);
        ReflectionTestUtils.setField(user, "senha", this.senha);
        ReflectionTestUtils.setField(user, "role", this.role);

        return user;
    }
}