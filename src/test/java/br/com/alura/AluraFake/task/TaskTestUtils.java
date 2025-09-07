package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.OpenTextTask;
import br.com.alura.AluraFake.task.request.OptionRequest;
import br.com.alura.AluraFake.task.request.SampleOptionsTask;
import br.com.alura.AluraFake.task.request.SampleTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.task.response.CreateChoiceTaskResponse;
import br.com.alura.AluraFake.task.response.CreateTaskResponse;

import java.util.List;

public class TaskTestUtils {


    public static Task createMockedTask(TaskType type, Integer order, String statement, Course course, List<Option> options) {
        final var task = createMockedTask(type, order, statement, course);
        task.setOptions(options);
        return task;
    }

    public static Task createMockedTask(Long id, TaskType type, Integer order, String statement, Course course, List<Option> options) {
        final var task = createMockedTask(id, type, order, statement, course);
        task.setOptions(options);
        return task;
    }

    public static Task createMockedTask(Long id, TaskType type, Integer order, String statement, Course course) {
        return Task.builder()
                .id(id)
                .type(type)
                .order(order)
                .statement(statement)
                .course(course)
                .build();
    }

    public static Task createMockedTask(TaskType type, Integer order, String statement, Course course) {
        return Task.builder()
                .type(type)
                .order(order)
                .statement(statement)
                .course(course)
                .build();
    }


    public static CreateTaskResponse createMockTestResponse(SampleTask taskRequest) {
        if (!(taskRequest instanceof OpenTextTask))
            return createMockTestResponse((SampleOptionsTask) taskRequest);
        return CreateChoiceTaskResponse.builder()
                .id(1L)
                .courseId(taskRequest.getCourseId())
                .statement(taskRequest.getStatement())
                .order(taskRequest.getOrder())
                .type(getType(taskRequest))
                .build();
    }

    public static CreateTaskResponse createMockTestResponse(SampleOptionsTask taskRequest) {
        return CreateChoiceTaskResponse.builder()
                .id(1L)
                .courseId(taskRequest.getCourseId())
                .statement(taskRequest.getStatement())
                .order(taskRequest.getOrder())
                .type(getType(taskRequest))
                .options(OptionTestUtils.copyFromOptionRequest(taskRequest.getOptions()))
                .build();
    }

    public static MultipleChoiceTask createMultipleChoiceTask(Integer order, String statement, Course course, List<OptionRequest> options) {
        return MultipleChoiceTask.builder()
                .courseId(course.getId())
                .statement(statement)
                .order(order)
                .options(options)
                .build();
    }

    public static SingleChoiceTask createSingleChoiceTask(Integer order, String statement, Course course, List<OptionRequest> options) {
        return SingleChoiceTask.builder()
                .courseId(course.getId())
                .statement(statement)
                .order(order)
                .options(options)
                .build();
    }

    public static OpenTextTask createOpenTextTask(Integer order, String statement, Course course) {
        return OpenTextTask.builder()
                .courseId(course.getId())
                .statement(statement)
                .order(order)
                .build();
    }

    private static TaskType getType(SampleTask taskRequest) {
        if (taskRequest instanceof SingleChoiceTask)
            return TaskType.SINGLE_CHOICE;
        if (taskRequest instanceof MultipleChoiceTask)
            return TaskType.MULTIPLE_CHOICE;
        if (taskRequest instanceof OpenTextTask)
            return TaskType.OPEN_TEXT;
        return null;
    }

}
