package br.com.karol.sistema.unit.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.mapper.LoginMapper;
import br.com.karol.sistema.domain.validator.usuario.login.LoginValidator;
import br.com.karol.sistema.domain.validator.usuario.login.PatternLoginValidator;
import br.com.karol.sistema.domain.valueobjects.Login;

@ExtendWith(MockitoExtension.class)
public class LoginMapperTest {

    @Mock
    private List<LoginValidator> validators;
    @Mock
    private PatternLoginValidator validator;

    @InjectMocks
    private LoginMapper mapper;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
        mapper = new LoginMapper(validators);
    }

    @Test
    void testToLogin() {
        // arrange
        String loginValue = "login";

        // act
        Login login = mapper.toLogin(loginValue);

        // assert
        assertNotNull(login);
        assertEquals(login.getValue(), loginValue);
    }
}