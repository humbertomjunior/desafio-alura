package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;

import java.util.List;

public class TaskTestUtils {

    public static Task createMockedTask(TaskType type, Integer order, String statement, Course course, List<Option> options) {
        final var task = createMockedTask(type, order, statement, course);
        task.setOptions(options);
        return task;
    }

    public static Task createMockedTask(TaskType type, Integer order, String statement, Course course) {
        return Task.builder()
                .id(1L)
                .type(type)
                .order(order)
                .statement(statement)
                .course(course)
                .build();
    }

}
