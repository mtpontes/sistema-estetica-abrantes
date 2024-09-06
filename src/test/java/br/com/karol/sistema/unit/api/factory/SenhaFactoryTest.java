package br.com.karol.sistema.unit.api.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.factory.SenhaFactory;
import br.com.karol.sistema.domain.formatter.SenhaEncoder;
import br.com.karol.sistema.domain.validator.SenhaValidator;
import br.com.karol.sistema.domain.valueobjects.Senha;

@ExtendWith(MockitoExtension.class)
public class SenhaFactoryTest {

    @Mock
    private SenhaValidator validator;
    @Mock
    private SenhaEncoder encoder;

    @InjectMocks
    private SenhaFactory mapper;


    @Test
    void testCreateSenha() {
        // arrange
        String senhaValue = "password123";
        when(encoder.encode(anyString())).thenReturn(senhaValue);

        // act
        Senha senha = mapper.criarSenha(senhaValue);

        // assert
        assertNotNull(senha);
        assertEquals(senha.getValue(), senhaValue);
    }
}