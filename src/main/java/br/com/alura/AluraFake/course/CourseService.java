package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.CourseMissesTaskTypeException;
import br.com.alura.AluraFake.util.CourseTasksOrdersNotInSequenceException;
import br.com.alura.AluraFake.util.NotFoundException;
import br.com.alura.AluraFake.util.UserIsNotInstructorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void publishCourse(Long id) {
        final var course = this.courseRepository.findById(id)
                .filter(foundCourse -> foundCourse.getStatus().equals(Status.BUILDING))
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        if (!this.areTasksOrdersSequenced(course))
            throw new CourseTasksOrdersNotInSequenceException("As atividades do curso selecionado não possuem uma sequência.", "task.order");

        if (!isThereAtLeastOneOfEachTaskType(course))
            throw new CourseMissesTaskTypeException("Conter ao menos uma atividade de cada tipo.", "task.type");


        course.setAsPublished();
        courseRepository.save(course);
    }

    public List<CourseAndTaskListItem> getCoursesByInstructorId(Long id) {
        final var user = userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("Usuário não encontrado.");
        if (!user.get().isInstructor())
            throw new UserIsNotInstructorException("Usuário com id: %s não é um instrutor.".formatted(id), "id");
        return this.courseRepository.findCoursesAndTasksByInstructorId(id);
    }

    private boolean areTasksOrdersSequenced(Course course) {
        final var sortedOrders = course.getTasks().stream().map(Task::getOrder).sorted().toList();

        if (!sortedOrders.get(0).equals(1))
            return false;

        return IntStream.range(0, sortedOrders.size()-1).allMatch(i -> sortedOrders.get(i).equals(sortedOrders.get(i+1)-1));
    }

    private boolean isThereAtLeastOneOfEachTaskType(Course course) {
        return course.getTasks().stream().map(Task::getType)
                .anyMatch(type -> type.equals(TaskType.OPEN_TEXT)) &&
                course.getTasks().stream().map(Task::getType)
                        .anyMatch(type -> type.equals(TaskType.SINGLE_CHOICE)) &&
                course.getTasks().stream().map(Task::getType)
                        .anyMatch(type -> type.equals(TaskType.MULTIPLE_CHOICE));

    }

}
