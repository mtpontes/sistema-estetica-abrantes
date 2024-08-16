package br.com.karol.sistema.utils;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;

public class UsuarioUtils {

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