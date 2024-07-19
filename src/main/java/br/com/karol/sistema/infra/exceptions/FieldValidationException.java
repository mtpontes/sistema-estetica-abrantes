package br.com.karol.sistema.infra.exceptions;

import lombok.Getter;

@Getter
public class FieldValidationException extends RuntimeException {;

    private static final String DEFAULT_MESSAGE = "inválido";
    private String fieldError;
    private String errorMessage;

    public FieldValidationException(String fieldName) {
        this.fieldError = fieldName.toLowerCase();
        this.errorMessage = DEFAULT_MESSAGE;
    }
    public FieldValidationException(String fieldName, String message) {
        this.fieldError = fieldName.toLowerCase();
        this.errorMessage = message;
    }
    
    @Override
    public String getMessage() {
        return String.format("%s : %s", this.fieldError, this.errorMessage);
    }
}