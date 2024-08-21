package br.com.karol.sistema.business.formatters;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SenhaFormatterImpl implements SenhaEncoder {

    private final PasswordEncoder encoder;

    
    @Override
    public String encode(String senha) {
        return encoder.encode(senha);
    }
}