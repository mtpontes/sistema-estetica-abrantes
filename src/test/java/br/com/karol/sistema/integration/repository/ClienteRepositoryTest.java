package br.com.karol.sistema.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Cliente;
import br.com.karol.sistema.infra.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);
    private static final String[] NAMES = {"fulano", "fulano", "fulano", "ciclano"};
    
    private static final long TOTAL_FULANO = Stream.of(NAMES)
        .filter(f -> f.equalsIgnoreCase("fulano"))
        .count();
    private static final long TOTAL_CICLANO = Stream.of(NAMES)
        .filter(f -> f.equalsIgnoreCase("ciclano"))
        .count();

    @Transactional
    @BeforeEach
    void setup() {
        saveClientesByName(NAMES);
    }

    private void saveClientesByName(String ... names) {
        for (int i = 0; i < names.length; i++) {
            var cliente = new Cliente();
            ReflectionTestUtils.setField(cliente, "nome", names[i]);
            repository.save(cliente);
        }
    }


    @Test
    void testFindAllByParams_semNomeCliente() {
        // act
        var results = repository.findAllByParams(null, PAGEABLE).getContent();

        // assert
        assertEquals(NAMES.length, results.size());
    }

    @Test
    void testFindAllByParams_comNomeExato01() {
        // act
        var results = repository.findAllByParams("fulano", PAGEABLE).getContent();

        // act
        assertEquals(TOTAL_FULANO, results.size());
    }
    @Test
    void testFindAllByParams_comNomeExato02() {
        // act
        var results = repository.findAllByParams("ciclano", PAGEABLE).getContent();

        // assert
        assertEquals(TOTAL_CICLANO, results.size());
    }
    
    @Test
    void testFindAllByParams_comNomeLike() {
        // act
        var results = repository.findAllByParams("fula", PAGEABLE).getContent();

        // assert
        assertEquals(TOTAL_FULANO, results.size());
    }
}