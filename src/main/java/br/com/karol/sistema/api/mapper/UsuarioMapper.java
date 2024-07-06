package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.domain.Usuario;

@Component
public class UsuarioMapper {

    private PasswordEncoder encoder;

    public UsuarioMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public Usuario toUsuario(CriarUsuarioDTO dto) {
        return new Usuario(dto.getNome(), dto.getLogin(), encoder.encode(dto.getSenha()));
    }

    public DadosUsuarioDTO toDadosUsuarioDTO(Usuario usuario) {
        return new DadosUsuarioDTO(usuario);
    }

    public List<DadosUsuarioDTO> toListDadosUsuarioDTO(List<Usuario> usuarios) {
        return usuarios.stream()
            .map(this::toDadosUsuarioDTO)
            .toList();
    }
}