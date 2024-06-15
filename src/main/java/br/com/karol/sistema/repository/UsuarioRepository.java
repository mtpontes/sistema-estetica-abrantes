package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
//OBS: CLASSE E TIPO (GENERICS)
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByLogin(String login);
}
