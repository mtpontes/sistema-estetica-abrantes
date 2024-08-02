package br.com.karol.sistema.unit.domain.formatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.karol.sistema.business.formatters.SenhaEncoderImpl;
import br.com.karol.sistema.domain.formatter.SenhaEncoder;

public class SenhaEncoderTest {
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SenhaEncoder senhaEncoder = new SenhaEncoderImpl(passwordEncoder);


    @Test
    void testEncodeSenha_comSucesso() {
        String senha = "Senha@123";
        String senhaCodificada = senhaEncoder.encode(senha);
        assertTrue(passwordEncoder.matches(senha, senhaCodificada));
    }

    @Test
    void testEncodeSenha_nula() {
        assertThrows(IllegalArgumentException.class, () -> senhaEncoder.encode(null));
    }
}