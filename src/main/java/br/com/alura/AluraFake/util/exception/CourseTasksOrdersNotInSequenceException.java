package br.com.alura.AluraFake.util.exception;

public class CourseTasksOrdersNotInSequenceException extends InvalidException {

    public CourseTasksOrdersNotInSequenceException(String message, String field) {
        super(message, field);
    }

}
