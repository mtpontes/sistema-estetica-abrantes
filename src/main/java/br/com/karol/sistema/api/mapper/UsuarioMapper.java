package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.api.factory.UsuarioFactory;
import br.com.karol.sistema.domain.Usuario;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    private final UsuarioFactory factory;


    public Usuario toUsuarioUser(CriarUsuarioDTO dto) {
        return factory.criarUsuarioUser(dto.getNome(), dto.getLogin(), dto.getSenha());
    }

    public Usuario toUsuarioAdmin(CriarUsuarioDTO dto) {
        return factory.criarUsuarioAdmin(dto.getNome(), dto.getLogin(), dto.getSenha());
    }

    public Usuario toUsuarioClient(CriarUsuarioDTO dto) {
        return factory.criarUsuarioClient(dto.getNome(), dto.getLogin(), dto.getSenha());
    }

    public DadosUsuarioDTO toDadosUsuarioDTO(Usuario usuario) {
        return new DadosUsuarioDTO(usuario);
    }

    public List<DadosUsuarioDTO> toListDadosUsuarioDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDadosUsuarioDTO).toList();
    }
}