package br.com.karol.sistema.infra.exceptions;

public class InvalidVOException extends RuntimeException {;

    private static final String DEFAULT_MESSAGE = " inválido";


    public <T> InvalidVOException(Class<T> classe) {
        super(classe.getSimpleName() + DEFAULT_MESSAGE);
    }
    public <T> InvalidVOException(Class<T> classe, String message) {
        super(String.format("%s : [%s]", classe.getSimpleName(), message));
    }
}