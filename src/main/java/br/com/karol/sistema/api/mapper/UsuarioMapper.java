package br.com.karol.sistema.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.karol.sistema.api.dto.usuario.CriarUsuarioDTO;
import br.com.karol.sistema.api.dto.usuario.DadosUsuarioDTO;
import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validations.usuario.login.LoginValidator;
import br.com.karol.sistema.domain.validations.usuario.senha.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    private final SenhaEncoder encoder;
    private final List<LoginValidator> loginValidators;
    private final SenhaValidator senhaValidator;


    public Usuario toUsuario(CriarUsuarioDTO dto) {
        return new Usuario(dto.getNome(), new Login(dto.getLogin(), loginValidators), new Senha(dto.getSenha(), senhaValidator, encoder));
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