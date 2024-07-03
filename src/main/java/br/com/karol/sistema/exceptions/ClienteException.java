package br.com.karol.sistema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ClienteException extends RuntimeException {;

    private static final String DEFAULT_MESSAGE = "Cliente não encontrado";


    public ClienteException(String mensagem) {
        super(mensagem);
    }
    public ClienteException() {
        super(DEFAULT_MESSAGE);
    }
}