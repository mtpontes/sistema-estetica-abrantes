package br.com.karol.sistema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ClienteException extends RuntimeException {
    public ClienteException(String mensagem) {
        super(mensagem);
    }
}
