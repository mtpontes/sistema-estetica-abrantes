package br.com.karol.sistema.unit.business.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karol.sistema.business.validators.UniqueLoginValidator;
import br.com.karol.sistema.infra.exceptions.FieldValidationException;
import br.com.karol.sistema.infra.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UniqueLoginValidatorTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UniqueLoginValidator validator;


    @Test
    void testDeveLancarExceptionQuandoExisteLogin() {
        when(repository.existsByLoginValue(anyString())).thenReturn(true);
        assertThrows(FieldValidationException.class, () -> validator.validate("login"));
    }

    @Test
    void testeDeveFazerNadaQuandoLoginNaoExiste() {
        when(repository.existsByLoginValue(anyString())).thenReturn(false);
        assertDoesNotThrow(() -> validator.validate("login"));
    }
}