package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.domain.Login;
import br.com.karol.sistema.repository.LoginRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class LoginService {

    private final String ERROR_MESSAGE = "Login não encontrado";

    private final LoginRepository repository;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }


    public Login salvar(Login login) {
        Login entity = repository.save(login);
        return entity;
    }

    public void removerLogin(String login) {
        if (!this.repository.existsByLogin(login)) throw new EntityNotFoundException(ERROR_MESSAGE);
        repository.deleteByLogin(login);
    }

    public Login editarLogin(Login login) {
        Login recovered = this.buscarLogin(login.getLogin());
        recovered.atualizarSenha(recovered.getLogin());
        repository.save(recovered);
        return recovered;
    }

    public List<Login> listarLogin() {
        return repository.findAll();
    }

    public Login buscarLogin(String login) {
        return this.repository.findByLogin(login)
            .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }
}