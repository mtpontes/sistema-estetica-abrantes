package br.com.karol.sistema.unit.api.mapper;

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

import br.com.karol.sistema.api.mapper.EmailMapper;
import br.com.karol.sistema.domain.validator.EmailValidator;
import br.com.karol.sistema.domain.valueobjects.Email;

@ExtendWith(MockitoExtension.class)
public class EmailMapperTest {

    @Mock
    private List<EmailValidator> validators;
    @Mock
    private EmailValidator validador;

    @InjectMocks
    private EmailMapper mapper;

    @BeforeEach
    void setup() {
        this.validators = List.of(validador);
        this.mapper = new EmailMapper(validators);
    }


    @Test
    void testToEmail() {
        String emailValue = "fulano@email.com";
        Email result = mapper.toEmail(emailValue);

        assertNotNull(result);
        assertEquals(emailValue, result.getValue());
    }

    @Test
    void testToEmailOrNullPassandoNull() {
        String emailValue = null;
        Email result = mapper.toEmailOrNull(emailValue);

        assertNull(result);
    }
    @Test
    void testToEmailOrNullPassandoBlank() {
        String emailValue = "";
        Email result = mapper.toEmailOrNull(emailValue);

        assertNull(result);
    }
}