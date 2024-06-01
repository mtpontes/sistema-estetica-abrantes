package br.com.karol.sistema.service;


import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.repository.UsuarioRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Data
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Usuario> salvar(Usuario usuario) {
        Usuario usuarioSalvo = repository.save(usuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = repository.findAll();
        return ResponseEntity.ok(usuarios);


    }

    public ResponseEntity<Usuario> buscarPorId(Integer id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());

        }
        return null;
    }

    public void removerPorId(Integer id) {
        repository.deleteById(id);
    }

    public ResponseEntity<Usuario> atualizar(Integer id, Usuario usuario) {
        // Verifica se o usuário existe no banco de dados
        Optional<Usuario> usuarioExistente = repository.findById(id);

        if (usuarioExistente.isPresent()) {
            // Copia os valores do usuário atualizado para o existente, exceto o ID
            Usuario usuarioParaAtualizar = usuarioExistente.get();
            usuarioParaAtualizar.setNome(usuario.getNome());
            usuarioParaAtualizar.setEmail(usuario.getEmail());
            usuarioParaAtualizar.setSenha(usuario.getSenha());


            // Salva as alterações no banco de dados
            Usuario usuarioSalvo = repository.save(usuarioParaAtualizar);
            return ResponseEntity.ok(usuarioSalvo);
        } else {
            // Retorna 404 Not Found se o usuário não existir
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}