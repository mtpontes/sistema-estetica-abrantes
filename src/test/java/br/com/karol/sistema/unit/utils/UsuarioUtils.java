package br.com.karol.sistema.unit.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;

public class UsuarioUtils {

    public static Usuario getUsuario() {
        Login login = getLogin("umLogin");
        Senha senha = getSenha("umaSenha");
        
        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(usuario, "id", 1L);
        ReflectionTestUtils.setField(usuario, "nome", "nome utils");
        ReflectionTestUtils.setField(usuario, "login", login);
        ReflectionTestUtils.setField(usuario, "senha", senha);
        ReflectionTestUtils.setField(usuario, "role", UserRole.ADMIN);

        return usuario;
    }
    public static Usuario getUsuario(PasswordEncoder encoder) {
        Login login = getLogin("umLogin");
        Senha senha = getSenha("umaSenha");
        senha.setValue(encoder.encode("123456789"));
        
        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(usuario, "id", 1L);
        ReflectionTestUtils.setField(usuario, "nome", "nome utils");
        ReflectionTestUtils.setField(usuario, "login", login);
        ReflectionTestUtils.setField(usuario, "senha", senha);
        ReflectionTestUtils.setField(usuario, "role", UserRole.ADMIN);

        return usuario;
    }

    public static Login getLogin(String value) {
        Login login = new Login();
        ReflectionTestUtils.setField(login, "value", value);
        return login;
    }
    public static Login getLogin() {
        Login login = new Login();
        ReflectionTestUtils.setField(login, "value", "any");
        return login;
    }
    
    public static Senha getSenha(String value) {
        Senha senha = new Senha();
        ReflectionTestUtils.setField(senha, "value", value);
        return senha;
    }
    public static Senha getSenha() {
        Senha senha = new Senha();
        ReflectionTestUtils.setField(senha, "value", "any");
        return senha;
    }
}