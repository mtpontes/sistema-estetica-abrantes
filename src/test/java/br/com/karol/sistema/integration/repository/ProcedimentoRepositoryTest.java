package br.com.karol.sistema.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Procedimento;
import br.com.karol.sistema.infra.repository.ProcedimentoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class ProcedimentoRepositoryTest {

    @Autowired
    private ProcedimentoRepository repository;

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @BeforeAll
    static void setup(@Autowired ProcedimentoRepository repository) {
        var procedimentos = List.of(
            new Procedimento(), 
            new Procedimento(), 
            new Procedimento()
        );

        var nomes = List.of(
            "harmonizacao f", 
            "harmonizacao p", 
            "preenchimento"
        );
        for (int i = 0; i < procedimentos.size(); i++) {
            ReflectionTestUtils.setField(
                procedimentos.get(i), "nome", nomes.get(i));
        }

        var valores = List.of(25.00, 50.00, 100.00);
        for (int i = 0; i < procedimentos.size(); i++) {
            ReflectionTestUtils.setField(
                procedimentos.get(i), "valor", valores.get(i));
        }

        repository.saveAll(procedimentos);
    }


    @Test
    void testFindAllByParams_semParams() {
        // arrange
        var totalOfElements = Long.valueOf(repository.count());

        // act
        var result = repository.findAllByParams(
            null, 
            null, 
            null, 
            PAGEABLE
        );
        
        // assert
        assertEquals(totalOfElements, result.getContent().size());
    }

    @Test
    void testFindAllByParams_comProcedimentoNome() {
        // arrange
        String procedimentoNome = "harmonizacao";

        var seed = repository.findAll().stream();
        Long totalOfElements = Long.valueOf(seed
            .filter(f -> f.getNome().contains(procedimentoNome))
            .count()
        );
            
        // act
        var result = repository.findAllByParams(
                procedimentoNome, 
                null, 
                null, 
                PAGEABLE
            )
            .getContent().size();
        
        // assert
        assertEquals(totalOfElements.intValue(), result);
    }
    
    @Test
    void testFindAllByParams_comMinValor() {
        // arrange
        double valorMin = 50.00;

        var seed = repository.findAll().stream();
        Long totalOfElements = Long.valueOf(seed
            .filter(f -> f.getValor() >= valorMin)
            .count()
        );
            
        // act
        var result = repository.findAllByParams(
                null, 
                valorMin, 
                null, 
                PAGEABLE
            )
            .getContent().size();
        
        // assert
        assertEquals(totalOfElements.intValue(), result);
    }

    @Test
    void testFindAllByParams_comMaxValor() {
        // arrange
        double valorMax = 50.00;

        var seed = repository.findAll().stream();
        Long totalOfElements = Long.valueOf(seed
            .filter(f -> f.getValor() <= valorMax)
            .count()
        );
            
        // act
        var result = repository.findAllByParams(
                null, 
                null, 
                valorMax, 
                PAGEABLE
            )
            .getContent().size();
        
        // assert
        assertEquals(totalOfElements.intValue(), result);
    }

    @Test
    void testFindAllByParams_entreMinMax() {
        // arrange
        double valorMin = 50.00;
        double valorMax = 50.00;

        var seed = repository.findAll().stream();
        Long totalOfElements = Long.valueOf(seed
            .filter(f -> f.getValor() >= valorMin)
            .filter(f -> f.getValor() <= valorMax)
            .count()
        );
            
        // act
        var result = repository.findAllByParams(
                null, 
                valorMin, 
                valorMax, 
                PAGEABLE
            )
            .getContent().size();
        
        // assert
        assertEquals(totalOfElements.intValue(), result);
    }

    @Test
    void testFindAllByParams_comTodosParams() {
        // arrange
        String procedimentoNome = "harmonizacao p";
        double valorMin = 50.00;
        double valorMax = 50.00;

        var seed = repository.findAll().stream();
        Long totalOfElements = Long.valueOf(seed
            .filter(f -> f.getValor() >= valorMin)
            .filter(f -> f.getValor() <= valorMax)
            .filter(f -> f.getNome().equalsIgnoreCase(procedimentoNome))
            .count()
        );
            
        // act
        var result = repository.findAllByParams(
                procedimentoNome, 
                valorMin, 
                valorMax, 
                PAGEABLE
            )
            .getContent().size();
        
        // assert
        assertEquals(totalOfElements.intValue(), result);
    }
}