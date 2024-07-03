package br.com.karol.sistema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.karol.sistema.domain.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

    boolean existsByLogin(String login);

    Optional<Login> findByLogin(String login);

    void deleteByLogin(String login);
}