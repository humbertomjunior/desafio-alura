package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.SampleOptionsTask;
import br.com.alura.AluraFake.task.request.SampleTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.task.response.CreatedChoiceTask;
import br.com.alura.AluraFake.task.response.CreatedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public CreatedTask createNewTask(SampleTask requestTask, TaskType type) {

        final var course = this.courseRepository.findById(requestTask.getCourseId())
                .filter(foundCourse -> foundCourse.getStatus().equals(Status.BUILDING))
                .orElseThrow(() -> new RuntimeException());

        final var task = this.buildTask(course, requestTask, type);

        final var createdTask = this.taskRepository.save(task);

        return this.buildCreatedTask(createdTask);

    }

    private Task buildTask(Course course, SampleOptionsTask optionsTask, TaskType type) {
        final var task = Task.builder()
                .statement(optionsTask.getStatement())
                .order(optionsTask.getOrder())
                .course(course)
                .type(type)
                .build();

        optionsTask.getOptions().forEach(task::addOption);

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

    private CreatedChoiceTask buildCreatedChoiceTask(Task task) {
        return CreatedChoiceTask.builder()
                .statement(task.getStatement())
                .order(task.getOrder())
                .courseId(task.getCourse().getId())
                .options(task.getOptions())
                .type(task.getType())
                .build();
    }

    private CreatedTask buildCreatedTask(Task task) {
        if (!task.getType().equals(TaskType.OPEN_TEXT))
            return this.buildCreatedChoiceTask(task);
        
        return CreatedTask.builder()
                .statement(task.getStatement())
                .order(task.getOrder())
                .courseId(task.getCourse().getId())
                .type(task.getType())
                .build();
    }

}
