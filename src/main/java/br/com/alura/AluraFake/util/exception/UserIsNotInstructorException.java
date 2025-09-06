package br.com.alura.AluraFake.util.exception;

public class UserIsNotInstructorException extends InvalidException {


    public UserIsNotInstructorException(String message, String field) {
        super(message, field);
    }
}
