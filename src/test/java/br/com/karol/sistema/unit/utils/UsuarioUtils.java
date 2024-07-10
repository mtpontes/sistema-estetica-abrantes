package br.com.karol.sistema.unit.utils;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;

public class UsuarioUtils {

    public static Usuario getUser() {
        Login login = getLogin("umLogin");
        Senha senha = getSenha("umaSenha");
        
        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(usuario, "login", login);
        ReflectionTestUtils.setField(usuario, "senha", senha);

        return usuario;
    }

    public static Login getLogin(String value) {
        Login login = new Login();
        ReflectionTestUtils.setField(login, "value", value);
        return login;
    }
    
    public static Senha getSenha(String value) {
        Senha senha = new Senha();
        ReflectionTestUtils.setField(senha, "value", value);
        return senha;
    }
}