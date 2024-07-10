package br.com.karol.sistema.infra.exceptions;

import lombok.Getter;

@Getter
public class FieldValidationException extends RuntimeException {;

    private static final String DEFAULT_MESSAGE = "inválido";
    private String fieldError;
    private String errorMessage;

    public FieldValidationException(String fieldName) {
        super(String.format("%s : %s", fieldName, DEFAULT_MESSAGE));
        this.fieldError = fieldName;
        this.errorMessage = DEFAULT_MESSAGE;
    }
    public FieldValidationException(String fieldName, String message) {
        super(String.format("%s : %s", fieldName, message));
        this.fieldError = fieldName;
        this.errorMessage = message;
    }
}