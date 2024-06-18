package br.com.karol.sistema.dto;

import br.com.karol.sistema.enums.UserRole;

public record RegisterDTO(String login, String password, String name,UserRole role) {
}
