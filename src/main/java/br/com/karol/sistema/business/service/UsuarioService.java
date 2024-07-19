package br.com.karol.sistema.business.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.karol.sistema.api.dto.usuario.AtualizarNomeUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaOutroUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.AtualizarSenhaUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.api.mapper.SenhaMapper;
import br.com.karol.sistema.api.mapper.UsuarioMapper;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.infra.exceptions.EntityNotFoundException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UsuarioService {

    private final String NOT_FOUND_MESSAGE = "Usuario não encontrado";

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final SenhaMapper senhaMapper;
    private final AuthenticationManager authenticationManager;


    public Usuario autenticarUsuario(String login, String senha) {
        var usernamePasswordToken = 
            new UsernamePasswordAuthenticationToken(login, senha);
        return (Usuario) authenticationManager
            .authenticate(usernamePasswordToken)
                .getPrincipal();
    }
 
    @Transactional
    public Usuario salvarUsuarioCliente(CriarUsuarioDTO dados) {
        Usuario client = mapper.toUsuarioClient(dados);
        return repository.save(client);
    }
    @Transactional
    public DadosUsuarioDTO salvarUsuario(CriarUsuarioDTO dados) {
        Usuario user = mapper.toUsuarioUser(dados);
        return mapper.toDadosUsuarioDTO(repository.save(user));
    }
    @Transactional
    public DadosUsuarioDTO adminSalvarUsuario(CriarUsuarioDTO dados) {
        Usuario admin = mapper.toUsuarioAdmin(dados);
        return mapper.toDadosUsuarioDTO(repository.save(admin));
    }

    public List<DadosUsuarioDTO> adminListarTodosUsuarios() {
        return mapper.toListDadosUsuarioDTO(repository.findAll());
    }

    public DadosUsuarioDTO getDadosUsuarioAtual(Usuario usuario) {
        return mapper.toDadosUsuarioDTO(usuario);
    }

    public DadosUsuarioDTO adminBuscarUsuarioPorID(Long id) {
        return mapper.toDadosUsuarioDTO(this.getUsuarioById(id));
    }

    @Transactional
    public DadosUsuarioDTO atualizarNomeUsuarioAtual(
        Usuario usuario, 
        AtualizarNomeUsuarioDTO update
    ) {
        usuario.atualizarDados(update.getNome());
        return mapper.toDadosUsuarioDTO(repository.save(usuario));
    }
    @Transactional
    public Usuario atualizarSenhaUsuarioAtual(
        Usuario usuarioAtual, 
        AtualizarSenhaUsuarioDTO dados
    ) {
        Senha novaSenha = senhaMapper.toSenha(dados.getNovaSenha());
        Usuario usuarioValidado = this.autenticarUsuario(
            usuarioAtual.getLogin(), 
            dados.getSenhaAtual()
        );
        usuarioValidado.atualizarSenha(novaSenha);
        
        return repository.save(usuarioValidado);
    }

    @Transactional
    public DadosUsuarioDTO adminAtualizarSenhaOutrosUsuarios(
        Long usuarioAlvoId, 
        AtualizarSenhaOutroUsuarioDTO update
    ) {
        Senha novaSenha = senhaMapper.toSenha(update.getSenha());
        Usuario alvo = this.getUsuarioById(usuarioAlvoId);
        alvo.atualizarSenha(novaSenha);
        
        return mapper.toDadosUsuarioDTO(repository.save(alvo));
    }

    @Transactional
    public void adminRemoverUsuario(Long id) {
        if (!this.repository.existsById(id))
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE);
        repository.deleteById(id);
    }

    private Usuario getUsuarioById(Long id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }
}