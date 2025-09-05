package br.com.alura.AluraFake.util;

public class TaskOrderOutOfSequenceException extends InvalidException {
    public TaskOrderOutOfSequenceException(String message, String field) {
        super(message, field);
    }
}
