package br.com.karol.sistema.business.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.api.mapper.UsuarioMapper;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.usuario.senha.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UsuarioService {

    private final String NOT_FOUND_MESSAGE = "Usuario não encontrado";


    private UsuarioRepository repository;
    private UsuarioMapper mapper;
    private SenhaValidator senhaValidator;
    private SenhaEncoder senhaEncoder;
    private AuthenticationManager authenticationManager;


    public Usuario autenticarUsuario(String login, String senha) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(login, senha);
        return (Usuario) authenticationManager.authenticate(usernamePasswordToken).getPrincipal();
    }
 
    @Transactional
    public DadosUsuarioDTO salvarUsuario(CriarUsuarioDTO usuario) {
        Usuario usuarioSalvo = repository.save(mapper.toUsuario(usuario));
        return mapper.toDadosUsuarioDTO(usuarioSalvo);
    }
    @Transactional
    public DadosUsuarioDTO adminSalvarUsuario(CriarUsuarioDTO dados) {
        Usuario usuario = mapper.toUsuario(dados);
        usuario.setRole(UserRole.ADMIN);
        
        return mapper.toDadosUsuarioDTO(repository.save(usuario));
    }

    public List<DadosUsuarioDTO> adminListarTodosUsuarios(Pageable pageable) {
        return mapper.toListDadosUsuarioDTO(repository.findAll());
    }

    public DadosUsuarioDTO getDadosUsuarioAtual(Usuario usuario) {
        return mapper.toDadosUsuarioDTO(usuario);
    }

    public DadosUsuarioDTO adminBuscarUsuarioPorID(String id) {
        return mapper.toDadosUsuarioDTO(this.getUsuarioById(id));
    }

    @Transactional
    public DadosUsuarioDTO atualizarUsuarioAtual(Usuario usuario, AtualizarUsuarioDTO update) {
        usuario.atualizarDados(update.getNome());
        return mapper.toDadosUsuarioDTO(repository.save(usuario));
    }
    @Transactional
    public Usuario atualizarSenhaUsuarioAtual(Usuario usuarioAtual, AtualizarSenhaUsuarioDTO dados) {
        Usuario usuarioValidado = this.autenticarUsuario(usuarioAtual.getLogin(), dados.getSenhaAtual());
        usuarioValidado.atualizarSenha(new Senha(dados.getNovaSenha(), senhaValidator, senhaEncoder));
        
        return repository.save(usuarioValidado);
    }

    @Transactional
    public DadosUsuarioDTO adminAtualizarSenhaOutrosUsuarios(String id, AtualizarSenhaOutroUsuarioDTO update) {
        Usuario alvo = this.getUsuarioById(id);

        alvo.atualizarSenha(new Senha(update.getSenha(), senhaValidator, senhaEncoder));
        
        return mapper.toDadosUsuarioDTO(repository.save(alvo));
    }

    @Transactional
    public void adminRemoverUsuario(String id) {
        if (!this.repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        repository.deleteById(id);
    }

    private Usuario getUsuarioById(String id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}