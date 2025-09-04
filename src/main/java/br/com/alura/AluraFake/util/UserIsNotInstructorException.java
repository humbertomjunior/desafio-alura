package br.com.alura.AluraFake.util;

public class UserIsNotInstructorException extends InvalidException {


    public UserIsNotInstructorException(String message, String field) {
        super(message, field);
    }
}
