package br.com.karol.sistema.unit.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.business.validators.UniqueEmailValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class UniqueEmailValidatorTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private UniqueEmailValidator validator;


    @Test
    void testDeveLancarExceptionQuandoExisteLogin() {
        when(repository.existsByEmailValue(anyString())).thenReturn(true);
        assertThrows(FieldValidationException.class, () -> validator.validate("login"));
    }

    @Test
    void testeDeveFazerNadaQuandoLoginNaoExiste() {
        when(repository.existsByEmailValue(anyString())).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate("login"));
    }
}