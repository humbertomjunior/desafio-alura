package br.com.alura.AluraFake.util;

public class InvalidUserException extends InvalidException {

    public InvalidUserException(String message, String field) {
        super(message, field);
    }

}
