package br.com.karol.sistema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.Usuario;
import br.com.karol.sistema.enums.UserRole;
import br.com.karol.sistema.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;

@Component
public class UserAdminDefaultCreation {

    @Autowired
	private UsuarioRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
	
    @Value("${admin.default.username}")
	private String username;
	@Value("${admin.default.password}")
	private String password;
    
	
	@PostConstruct
	public void createUserAdmin() {
		if(!userRepository.existsByLogin(username)) {
            Usuario usuario = new Usuario("Default Admin", username, encoder.encode(password));
            usuario.setRole(UserRole.ADMIN);
			userRepository.save(usuario);
		}
	}
}