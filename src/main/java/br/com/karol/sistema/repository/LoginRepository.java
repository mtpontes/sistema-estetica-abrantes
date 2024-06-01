package br.com.karol.sistema.repository;

import br.com.karol.sistema.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
}
