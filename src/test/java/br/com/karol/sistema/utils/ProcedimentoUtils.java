package br.com.karol.sistema.utils;

import java.time.LocalTime;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Procedimento;

public class ProcedimentoUtils {

    public static Procedimento getProcedimento() {
        Procedimento procedimento = new Procedimento();
        
        ReflectionTestUtils.setField(procedimento, "id", 1L);
        ReflectionTestUtils.setField(procedimento, "nome", "nome utils");
        ReflectionTestUtils.setField(procedimento, "descricao", "descricao utils");
        ReflectionTestUtils.setField(procedimento, "duracao", LocalTime.now());
        ReflectionTestUtils.setField(procedimento, "valor", 50.00);

        return procedimento;
    }
}