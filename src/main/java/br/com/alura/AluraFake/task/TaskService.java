package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.SampleOptionsTask;
import br.com.alura.AluraFake.task.request.SampleTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.task.response.CreateChoiceTaskResponse;
import br.com.alura.AluraFake.task.response.CreateTaskResponse;
import br.com.alura.AluraFake.util.exception.DuplicateStatementException;
import br.com.alura.AluraFake.util.exception.NotFoundException;
import br.com.alura.AluraFake.util.exception.TaskOrderOutOfSequenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CreateTaskResponse createNewTask(SampleTask requestTask, TaskType type) {

        final var course = this.courseRepository.findById(requestTask.getCourseId())
                .filter(foundCourse -> foundCourse.getStatus().equals(Status.BUILDING))
                .orElseThrow(() -> new NotFoundException("Não foi encontrado um curso com status BUILDING e id: %s".formatted(requestTask.getCourseId())));

        if (this.statementAlreadyExists(requestTask, course))
            throw new DuplicateStatementException("O curso não pode ter duas questões com o mesmo enunciado.", "statement");

        if (!isOrderValid(requestTask.getOrder(), course.getTasks()))
            throw new TaskOrderOutOfSequenceException("A order da atividade não pode ser adicionada pois deixará espaço(s) vazio(s).", "task.order");

        final var task = this.buildTask(course, requestTask, type);

        final var savedTask = this.saveTask(task, course.getTasks());

        return this.buildCreatedTask(savedTask);

    }

    private Task buildTask(Course course, SampleOptionsTask optionsTask, TaskType type) {
        final var task = Task.builder()
                .statement(optionsTask.getStatement())
                .order(optionsTask.getOrder())
                .course(course)
                .type(type)
                .build();

        optionsTask.getOptions()
                .stream()
                .map(optionRequest -> Option.builder().option(optionRequest.getOption()).isCorrect(optionRequest.isCorrect()).build())
                .forEach(task::addOption);

        return task;
    }

    private Task buildTask(Course course, SampleTask task, TaskType type) {

        if (task instanceof SingleChoiceTask | task instanceof MultipleChoiceTask)
            return buildTask(course, (SampleOptionsTask) task, type);

        return Task.builder()
                .statement(task.getStatement())
                .order(task.getOrder())
                .course(course)
                .type(type)
                .build();
    }

    private CreateChoiceTaskResponse buildCreatedChoiceTask(Task task) {
        return CreateChoiceTaskResponse.builder()
                .id(task.getId())
                .statement(task.getStatement())
                .order(task.getOrder())
                .courseId(task.getCourse().getId())
                .options(task.getOptions())
                .type(task.getType())
                .build();
    }

    private CreateTaskResponse buildCreatedTask(Task task) {
        if (!task.getType().equals(TaskType.OPEN_TEXT))
            return this.buildCreatedChoiceTask(task);

        return CreateTaskResponse.builder()
                .id(task.getId())
                .statement(task.getStatement())
                .order(task.getOrder())
                .courseId(task.getCourse().getId())
                .type(task.getType())
                .build();
    }

    private boolean statementAlreadyExists(SampleTask requestTask, Course course) {
        final var tasksStatements = course.getTasks().stream().map(Task::getStatement).toList();
        return tasksStatements.stream().anyMatch(statement -> statement.equals(requestTask.getStatement()));
    }

    private boolean isOrderValid(Integer order, List<Task> otherTasksFromCourse) {
        if (!otherTasksFromCourse.isEmpty()) {
            final var orderedOrders = otherTasksFromCourse.stream().map(Task::getOrder).sorted().toList();
            final var largestValidOrder = orderedOrders.get(orderedOrders.size() - 1) + 1;
            return(order <= largestValidOrder);
        }
        return order == 1;
    }

    private Task saveTask(Task requestTask, List<Task> otherTasksFromCourse) {
        if (otherTasksFromCourse.isEmpty())
            return taskRepository.save(requestTask);

        final var repeatedOrder = otherTasksFromCourse.stream()
                .flatMap(task -> Optional.ofNullable(task.getOrder()).stream())
                .anyMatch(order -> order.equals(requestTask.getOrder()));

        if (!repeatedOrder)
            return taskRepository.save(requestTask);

        final var updatedTasks = otherTasksFromCourse.stream()
                .filter(task -> task.getOrder() >= requestTask.getOrder())
                .map(Task::increaseOrder)
                .toList();

        final var savedTask = taskRepository.save(requestTask);

        taskRepository.saveAll(updatedTasks);

        return savedTask;
    }
}
