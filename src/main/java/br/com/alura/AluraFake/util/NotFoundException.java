package br.com.alura.AluraFake.util;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    protected String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
