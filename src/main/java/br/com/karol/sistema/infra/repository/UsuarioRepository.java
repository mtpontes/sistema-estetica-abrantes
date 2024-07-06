package br.com.karol.sistema.infra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.karol.sistema.domain.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    UserDetails findByLogin(String login);
    
    boolean existsByLogin(String login);
}