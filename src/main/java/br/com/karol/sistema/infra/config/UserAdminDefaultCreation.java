package br.com.karol.sistema.infra.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.domain.enums.UserRole;
import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.usuario.login.LoginValidator;
import br.com.karol.sistema.domain.validator.usuario.senha.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Login;
import br.com.karol.sistema.domain.valueobjects.Senha;
import br.com.karol.sistema.infra.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;

@Component
public class UserAdminDefaultCreation {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private SenhaEncoder encoder;
	@Autowired
	private SenhaValidator senhaValidators;
	@Autowired
	private List<LoginValidator> loginValidators;

	@Value("${admin.default.username}")
	private String username;
	@Value("${admin.default.password}")
	private String password;


	@PostConstruct
	public void createUserAdmin() {
		if(!userRepository.existsByLoginValue(username)) {
			Login login = new Login(username, loginValidators);
			Senha senha = new Senha(password, senhaValidators, encoder);

			Usuario usuario = new Usuario("DefaultAdmin", login, senha);
			usuario.setRole(UserRole.ADMIN);
			
			userRepository.save(usuario);
		}
	}
}