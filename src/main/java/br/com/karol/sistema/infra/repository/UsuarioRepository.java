package br.com.karol.sistema.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLoginValue(String login);
    
    boolean existsByLoginValue(String login);
}