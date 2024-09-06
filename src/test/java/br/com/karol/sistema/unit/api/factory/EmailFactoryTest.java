package br.com.karol.sistema.unit.api.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.api.factory.EmailFactory;
import br.com.karol.sistema.domain.validator.EmailValidator;
import br.com.karol.sistema.domain.valueobjects.Email;

@ExtendWith(MockitoExtension.class)
public class EmailFactoryTest {

    @Mock
    private List<EmailValidator> validators;
    @Mock
    private EmailValidator validador;

    @InjectMocks
    private EmailFactory mapper;

    @BeforeEach
    void setup() {
        this.validators = List.of(validador);
        this.mapper = new EmailFactory(validators);
    }


    @Test
    void testCreateEmail() {
        String emailValue = "fulano@email.com";
        Email result = mapper.createEmail(emailValue);

        assertNotNull(result);
        assertEquals(emailValue, result.getValue());
    }

    @Test
    void testToEmailOrNull_passandoNull() {
        String emailValue = null;
        Email result = mapper.toEmailOrNull(emailValue);

        assertNull(result);
    }
    @Test
    void testToEmailOrNull_passandoBlank() {
        String emailValue = "";
        Email result = mapper.toEmailOrNull(emailValue);

        assertNull(result);
    }
}