package br.com.karol.sistema.service;

import br.com.karol.sistema.domain.Login;
import br.com.karol.sistema.repository.LoginRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Data
public class LoginService {
    private final LoginRepository repository;

    @Autowired
    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Login> salvar(Login login) {
        Login login1 = repository.save(login);
        return ResponseEntity.ok(login1);

    }

    public Login buscarLogin(String login) {
        List<Login> logins = repository.findAll();
        for (Login login1 : logins) {
            if (login.equals(login1.getLogin())) {
                return login1;
            }
        }

        return null;
    }

    public ResponseEntity<Login> removerLogin(String login) {
        Login login1 = buscarLogin(login);
        repository.delete(login1);
        return ResponseEntity.ok(login1);

    }

    public ResponseEntity<Login> editarLogin(Login login) {
        Login login1 = buscarLogin(login.getLogin());
        login1.setLogin(login.getLogin());
        repository.save(login1);
        return ResponseEntity.ok(login1);
    }

    public List<Login> listarLogin() {
        return repository.findAll();

    }
}
