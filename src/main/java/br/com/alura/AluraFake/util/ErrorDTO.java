package br.com.alura.AluraFake.util;

import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

public class ErrorDTO {

    private final String message;

    public ErrorDTO(String message) {
        Assert.notNull(message, "message description must not be null");
        Assert.isTrue(!message.isEmpty(), "message description must not be null");
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
