package br.com.karol.sistema.builder;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;

public class UsuarioTestFactory {

    public static Usuario getUsuarioAdmin() {
        return createUser()
            .role(UserRole.ADMIN)
            .build();
    }

    public static Usuario getUsuarioUser() {
        return createUser()
            .role(UserRole.USER)
            .build();
    }

    public static Usuario getUsuarioClient() {
        return createUser()
            .role(UserRole.CLIENT)
            .build();
    }

    private static UsuarioTestBuilder createUser() {
        return new UsuarioTestBuilder()
            .id(1L)
            .nome("default name")
            .login("default login")
            .senha("default senha");
    }
}   