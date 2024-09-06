package br.com.karol.sistema.builder;

import org.springframework.test.util.ReflectionTestUtils;

import br.com.karol.sistema.domain.Endereco;

public class EnderecoTestFactory {

    public static Endereco getDefaultEndereco() {
        Endereco endereco = new Endereco();
        ReflectionTestUtils.setField(endereco, "rua", "umaRua");
        ReflectionTestUtils.setField(endereco, "numero", "umNumero");
        ReflectionTestUtils.setField(endereco, "cidade", "umaCidade");
        ReflectionTestUtils.setField(endereco, "bairro", "umBairro");
        ReflectionTestUtils.setField(endereco, "estado", "umEstado");
        return endereco;
    }
}