package br.com.alura.AluraFake.util.exception;

public class DuplicateStatementException extends InvalidException {

    public DuplicateStatementException(String message, String field) {
        super(message, field);
    }

}
