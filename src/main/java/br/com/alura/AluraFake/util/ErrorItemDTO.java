package br.com.alura.AluraFake.util;

import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ErrorItemDTO extends ErrorDTO {

    private final String field;
    private final String message;

    public ErrorItemDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public ErrorItemDTO(ObjectError objectError) {
        this(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public ErrorItemDTO(String field, String message) {
        super(message);
        Assert.notNull(field, "field description must not be null");
        Assert.isTrue(!field.isEmpty(), "field description must not be null");
        Assert.notNull(message, "message description must not be null");
        Assert.isTrue(!message.isEmpty(), "message description must not be null");
        this.field = field;
        this.message = message;
    }


    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
