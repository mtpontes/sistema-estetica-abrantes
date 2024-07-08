package br.com.karol.sistema.domain.formatter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SenhaEncoderImpl implements SenhaEncoder {

    private final PasswordEncoder encoder;

    
    @Override
    public String encode(String senha) {
        return encoder.encode(senha);
    }
}