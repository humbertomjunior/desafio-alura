package br.com.alura.AluraFake.util;

public class DuplicateStatementException extends InvalidException {

    public DuplicateStatementException(String message, String field) {
        super(message, field);
    }

}
