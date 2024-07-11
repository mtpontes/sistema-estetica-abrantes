package br.com.karol.sistema.unit.domain.formatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.formatter.SenhaEncoderImpl;

public class SenhaEncoderTest {
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SenhaEncoder senhaEncoder = new SenhaEncoderImpl(passwordEncoder);


    @Test
    void testEncodeSenhaComSucesso() {
        String senha = "Senha@123";
        String senhaCodificada = senhaEncoder.encode(senha);
        assertTrue(passwordEncoder.matches(senha, senhaCodificada));
    }

    @Test
    void testEncodeSenhaNula() {
        assertThrows(IllegalArgumentException.class, () -> senhaEncoder.encode(null));
    }
}