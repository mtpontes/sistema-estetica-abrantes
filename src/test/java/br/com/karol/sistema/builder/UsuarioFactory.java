package br.com.karol.sistema.builder;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;

public class UsuarioFactory {

    public static Usuario getUsuario() {
        return new UsuarioBuilder()
            .id(1L)
            .nome("default name")
            .login("default login")
            .senha("default senha")
            .role(UserRole.ADMIN)
            .build();
    }
}   