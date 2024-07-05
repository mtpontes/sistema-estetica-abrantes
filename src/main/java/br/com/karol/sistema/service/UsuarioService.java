package br.com.karol.sistema.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.dto.usuario.AtualizarUsuarioDTO;
import br.com.karol.sistema.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.exceptions.EntityNotFoundException;
import br.com.karol.sistema.mapper.UsuarioMapper;
import br.com.karol.sistema.repository.UsuarioRepository;


@Service
@Transactional
public class UsuarioService {

    private final String NOT_FOUND_MESSAGE = "Usuario não encontrado";

    private UsuarioRepository repository;
    private UsuarioMapper mapper;
    private PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper, PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }


    public DadosUsuarioDTO salvarUsuario(CriarUsuarioDTO usuario) {
        Usuario usuarioSalvo = repository.save(mapper.toUsuario(usuario));
        return mapper.toDadosUsuarioDTO(usuarioSalvo);
    }
    public DadosUsuarioDTO adminSalvarUsuario(CriarUsuarioDTO dados) {
        Usuario usuario = mapper.toUsuario(dados);
        usuario.setRole(UserRole.ADMIN);
        
        return mapper.toDadosUsuarioDTO(repository.save(usuario));
    }

    public List<DadosUsuarioDTO> listarTodosUsuarios(Pageable pageable) {
        return mapper.toListDadosUsuarioDTO(repository.findAll());
    }

    public DadosUsuarioDTO getDadosUsuarioAtual(Usuario usuario) {
        return mapper.toDadosUsuarioDTO(usuario);
    }

    public DadosUsuarioDTO adminBuscarUsuarioPorID(String id) {
        return mapper.toDadosUsuarioDTO(this.getUsuarioById(id));
    }

    public void removerUsuario(String id) {
        if (!this.repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        repository.deleteById(id);
    }

    public DadosUsuarioDTO atualizarUsuarioAtual(Usuario usuario, AtualizarUsuarioDTO update) {
        Usuario alvo = this.getUsuarioById(usuario.getId());
        alvo.atualizarDados(update.getNome(), encoder.encode(update.getSenha()));

        return mapper.toDadosUsuarioDTO(repository.save(alvo));
    }
    public DadosUsuarioDTO adminAtualizarSenhaOutrosUsuarios(String id, AtualizarSenhaOutroUsuarioDTO update) {
        Usuario alvo = this.getUsuarioById(id);
        alvo.setSenha(encoder.encode(update.getSenha()));
        
        return mapper.toDadosUsuarioDTO(repository.save(alvo));
    }

    private Usuario getUsuarioById(String id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}