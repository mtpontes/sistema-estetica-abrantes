package br.com.karol.sistema.unit.api.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.factory.LoginFactory;
import br.com.karol.sistema.business.validators.PatternLoginValidator;
import br.com.karol.sistema.domain.validator.LoginValidator;
import br.com.karol.sistema.domain.valueobjects.Login;

@ExtendWith(MockitoExtension.class)
public class LoginFactoryTest {

    @Mock
    private List<LoginValidator> validators;
    @Mock
    private PatternLoginValidator validator;

    @InjectMocks
    private LoginFactory mapper;

    @BeforeEach
    void setup() {
        validators = List.of(validator);
        mapper = new LoginFactory(validators);
    }

    @Test
    void testCreateLogin() {
        // arrange
        String loginValue = "login";

        // act
        Login login = mapper.criarLogin(loginValue);

        // assert
        assertNotNull(login);
        assertEquals(login.getValue(), loginValue);
    }
}