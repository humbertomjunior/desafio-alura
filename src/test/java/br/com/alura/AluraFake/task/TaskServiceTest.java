package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.CourseTestUtils;
import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.OpenTextTask;
import br.com.alura.AluraFake.task.request.SampleOptionsTask;
import br.com.alura.AluraFake.task.request.SampleTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.task.response.CreateChoiceTaskResponse;
import br.com.alura.AluraFake.task.response.CreateTaskResponse;
import br.com.alura.AluraFake.util.exception.DuplicateStatementException;
import br.com.alura.AluraFake.util.exception.NotFoundException;
import br.com.alura.AluraFake.util.exception.TaskOrderOutOfSequenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void createNewTask__should_return_new_created_open_text_task() {
        SampleTask task = OpenTextTask.builder()
                .statement("New open text task")
                .order(1)
                .courseId(1L)
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        Task createdTask = TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 1, "New open text task", foundCourse);

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));
        when(taskRepository.save(any())).thenReturn(createdTask);

        CreateTaskResponse createTaskResponse = taskService.createNewTask(task, TaskType.OPEN_TEXT);

        assertAll(
                () -> assertEquals(1L, createTaskResponse.getId()),
                () -> assertEquals(task.getStatement(), createTaskResponse.getStatement()),
                () -> assertEquals(task.getOrder(), createTaskResponse.getOrder()),
                () -> assertEquals(task.getCourseId(), createTaskResponse.getCourseId()),
                () -> assertEquals(TaskType.OPEN_TEXT, createTaskResponse.getType())
        );
    }

    @Test
    void createNewTask__should_return_new_created_single_choice_task() {

        final var taskType = TaskType.SINGLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var statement = "New single choice task";

        SampleOptionsTask task = SingleChoiceTask.builder()
                .statement(statement)
                .order(1)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        List<Option> options = OptionTestUtils.createMockedOptions(optionsQuantity, correctAnswers);

        Task createdTask = TaskTestUtils.createMockedTask(taskType, 1, statement, foundCourse, options);

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));
        when(taskRepository.save(any())).thenReturn(createdTask);

        CreateChoiceTaskResponse createTaskResponse = (CreateChoiceTaskResponse) taskService.createNewTask(task, taskType);

        for (int i = 0; i < task.getOptions().size(); i++) {
            assertEquals(task.getOptions().get(i).getOption(), createTaskResponse.getOptions().get(i).getOption());
            assertEquals(task.getOptions().get(i).isCorrect(), createTaskResponse.getOptions().get(i).isCorrect());
        }

        assertAll(
                () -> assertEquals(1L, createTaskResponse.getId()),
                () -> assertEquals(task.getStatement(), createTaskResponse.getStatement()),
                () -> assertEquals(task.getOrder(), createTaskResponse.getOrder()),
                () -> assertEquals(task.getCourseId(), createTaskResponse.getCourseId()),
                () -> assertEquals(taskType, createTaskResponse.getType())
        );

    }

    @Test
    void createNewTask__should_return_new_created_multiple_choice_task() {

        final var taskType = TaskType.MULTIPLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var statement = "New multiple choice task";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement(statement)
                .order(1)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        List<Option> options = OptionTestUtils.createMockedOptions(optionsQuantity, correctAnswers);

        Task createdTask = TaskTestUtils.createMockedTask(taskType, 1, statement, foundCourse, options);

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));
        when(taskRepository.save(any())).thenReturn(createdTask);

        CreateChoiceTaskResponse createTaskResponse = (CreateChoiceTaskResponse) taskService.createNewTask(task, taskType);

        for (int i = 0; i < task.getOptions().size(); i++) {
            assertEquals(task.getOptions().get(i).getOption(), createTaskResponse.getOptions().get(i).getOption());
            assertEquals(task.getOptions().get(i).isCorrect(), createTaskResponse.getOptions().get(i).isCorrect());
        }

        assertAll(
                () -> assertEquals(1L, createTaskResponse.getId()),
                () -> assertEquals(task.getStatement(), createTaskResponse.getStatement()),
                () -> assertEquals(task.getOrder(), createTaskResponse.getOrder()),
                () -> assertEquals(task.getCourseId(), createTaskResponse.getCourseId()),
                () -> assertEquals(taskType, createTaskResponse.getType())
        );

    }

    @Test
    void createNewTask__should_return_new_created_multiple_choice_task_with_course_tasks_not_empty() {

        final var taskType = TaskType.MULTIPLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var taskFromCourseStatement = "New multiple choice task";
        final var newTaskStatement = "New multiple choice task 2";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement(newTaskStatement)
                .order(2)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        Task taskFromCourse = TaskTestUtils.createMockedTask(taskType, 1, taskFromCourseStatement, foundCourse);

        CourseTestUtils.setTasks(foundCourse, List.of(taskFromCourse));

        List<Option> options = OptionTestUtils.createMockedOptions(optionsQuantity, correctAnswers);

        Task createdTask = TaskTestUtils.createMockedTask(taskType, 2, newTaskStatement, foundCourse, options);

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));
        when(taskRepository.save(any())).thenReturn(createdTask);

        CreateChoiceTaskResponse createTaskResponse = (CreateChoiceTaskResponse) taskService.createNewTask(task, taskType);

        for (int i = 0; i < task.getOptions().size(); i++) {
            assertEquals(task.getOptions().get(i).getOption(), createTaskResponse.getOptions().get(i).getOption());
            assertEquals(task.getOptions().get(i).isCorrect(), createTaskResponse.getOptions().get(i).isCorrect());
        }

        assertAll(
                () -> assertEquals(1L, createTaskResponse.getId()),
                () -> assertEquals(task.getStatement(), createTaskResponse.getStatement()),
                () -> assertEquals(task.getOrder(), createTaskResponse.getOrder()),
                () -> assertEquals(task.getCourseId(), createTaskResponse.getCourseId()),
                () -> assertEquals(taskType, createTaskResponse.getType())
        );

    }

    @Test
    void createNewTask__should_return_new_created_multiple_choice_task_with_course_tasks_not_empty_repeated_order() {

        final var taskType = TaskType.SINGLE_CHOICE;
        final var optionsQuantity = 4;
        final var correctAnswers = 1;
        final var taskFromCourseStatement = "New single choice task";
        final var newTaskStatement = "New single choice task 4";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement(newTaskStatement)
                .order(1)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        CourseTestUtils.setTasks(
                foundCourse,
                List.of(
                        TaskTestUtils.createMockedTask(taskType, 1, taskFromCourseStatement.concat(" 1"), foundCourse),
                        TaskTestUtils.createMockedTask(taskType, 2, taskFromCourseStatement.concat(" 2"), foundCourse),
                        TaskTestUtils.createMockedTask(taskType, 3, taskFromCourseStatement.concat(" 3"), foundCourse)
                ));

        List<Option> options = OptionTestUtils.createMockedOptions(optionsQuantity, correctAnswers);

        Task createdTask = TaskTestUtils.createMockedTask(taskType, 1, newTaskStatement, foundCourse, options);

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));
        when(taskRepository.save(any())).thenReturn(createdTask);

        CreateChoiceTaskResponse createTaskResponse = (CreateChoiceTaskResponse) taskService.createNewTask(task, taskType);

        for (int i = 0; i < task.getOptions().size(); i++) {
            assertEquals(task.getOptions().get(i).getOption(), createTaskResponse.getOptions().get(i).getOption());
            assertEquals(task.getOptions().get(i).isCorrect(), createTaskResponse.getOptions().get(i).isCorrect());
        }

        assertAll(
                () -> assertEquals(1L, createTaskResponse.getId()),
                () -> assertEquals(task.getStatement(), createTaskResponse.getStatement()),
                () -> assertEquals(task.getOrder(), createTaskResponse.getOrder()),
                () -> assertEquals(task.getCourseId(), createTaskResponse.getCourseId()),
                () -> assertEquals(taskType, createTaskResponse.getType())
        );

    }

    @Test
    void createNewTask__should_throw_not_found_exception() {

        final var taskType = TaskType.MULTIPLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var statement = "New multiple choice task";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement(statement)
                .order(1)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.ofNullable(null));

        assertThrows(NotFoundException.class, () -> taskService.createNewTask(task, taskType));

    }

    @Test
    void createNewTask__should_throw_duplicated_statement_exception() {

        final var taskType = TaskType.MULTIPLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var statement = "New multiple choice task";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement(statement)
                .order(1)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        Task taskFromCourse = TaskTestUtils.createMockedTask(taskType, 1, statement, foundCourse);

        CourseTestUtils.setTasks(foundCourse, List.of(taskFromCourse));

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));

        assertThrows(DuplicateStatementException.class, () -> taskService.createNewTask(task, taskType));

    }

    @Test
    void createNewTask__should_throw_task_order_out_of_sequence_exception() {

        final var taskType = TaskType.SINGLE_CHOICE;
        final var optionsQuantity = 3;
        final var correctAnswers = 1;
        final var statement = "New single choice task";


        SampleOptionsTask task = MultipleChoiceTask.builder()
                .statement("New Single Choice Task 2")
                .order(3)
                .courseId(1L)
                .options(OptionTestUtils.createMockedOptionRequests(optionsQuantity, correctAnswers))
                .build();

        Course foundCourse = CourseTestUtils.createMockedCourse();

        Task taskFromCourse = TaskTestUtils.createMockedTask(taskType, 1, statement, foundCourse);

        CourseTestUtils.setTasks(foundCourse, List.of(taskFromCourse));

        when(courseRepository.findById(task.getCourseId())).thenReturn(Optional.of(foundCourse));

        assertThrows(TaskOrderOutOfSequenceException.class, () -> taskService.createNewTask(task, taskType));

    }
}
