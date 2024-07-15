package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    private final LoginMapper loginMapper;
    private final SenhaMapper senhaMapper;


    public Usuario toUsuarioUser(CriarUsuarioDTO dto) {
        Login login = this.loginMapper.toLogin(dto.getLogin());
        Senha senha = this.senhaMapper.toSenha(dto.getSenha());

        return new Usuario(dto.getNome(), login, senha, UserRole.USER);
    }

    public Usuario toUsuarioAdmin(CriarUsuarioDTO dto) {
        Login login = this.loginMapper.toLogin(dto.getLogin());
        Senha senha = this.senhaMapper.toSenha(dto.getSenha());

        return new Usuario(dto.getNome(), login, senha, UserRole.ADMIN);
    }

    public Usuario toUsuarioClient(CriarUsuarioDTO dto) {
        Login login = this.loginMapper.toLogin(dto.getLogin());
        Senha senha = this.senhaMapper.toSenha(dto.getSenha());

        return new Usuario(dto.getNome(), login, senha, UserRole.CLIENT);
    }

    public DadosUsuarioDTO toDadosUsuarioDTO(Usuario usuario) {
        return new DadosUsuarioDTO(usuario);
    }

    public List<DadosUsuarioDTO> toListDadosUsuarioDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDadosUsuarioDTO).toList();
    }
}