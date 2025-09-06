package br.com.alura.AluraFake.util.exception;

import lombok.Getter;

@Getter
public class InvalidException extends RuntimeException {

    protected String field;
    protected String message;

    public InvalidException(String message, String field) {
        super(message);
        this.field = field;
        this.message = message;
    }
}
