package br.com.karol.sistema.infra.exceptions;

public class EntityNotFoundException extends RuntimeException {;

    private static final String DEFAULT_MESSAGE = "Não encontrado";


    public EntityNotFoundException(String mensagem) {
        super(mensagem);
    }
    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}