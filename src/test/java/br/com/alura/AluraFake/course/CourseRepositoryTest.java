package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.OptionTestUtils;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskTestUtils;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserTestUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findById__should_return_course() {

        User instructor = UserTestUtils.createMockInstructor("Humberto", "Humberto@email.com");
        UserTestUtils.setId(instructor, 1L);

        Course course = new Course("Spring Boot", "Curso Zero to Hero Spring Boot", instructor);

        Course savedCourse = courseRepository.save(course);

        Optional<Course> result = courseRepository.findById(savedCourse.getId());

        assertEquals("Humberto", result.map(Course::getInstructor).map(User::getName).orElse(null));
        assertEquals("Humberto@email.com", result.map(Course::getInstructor).map(User::getEmail).orElse(null));
        assertEquals("Spring Boot", result.map(Course::getTitle).orElse(null));
        assertEquals("Curso Zero to Hero Spring Boot", result.map(Course::getDescription).orElse(null));
    }

    @Test
    void findCoursesAndTasksByInstructorId__should_return_object_containing_course_and_tasks_quantity() {

        final var courseTitle = "Spring Boot";
        final var courseDescription = "Curso Zero to Hero Spring Boot";


        User instructor = UserTestUtils.createMockInstructor("Humberto", "Humberto@email.com");
        UserTestUtils.setId(instructor, 1L);

        Course course = new Course(courseTitle, courseDescription, instructor);

        List<Task> tasks = List.of(
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 1, "Questão 1", course, OptionTestUtils.createMockedOptions(5, 3)),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 2, "Questão 2", course, OptionTestUtils.createMockedOptions(5, 1)),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 3, "Questão 3", course, OptionTestUtils.createMockedOptions(3, 1)),
                TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 4, "Questão 4", course),
                TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 5, "Questão 5", course),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 6, "Questão 6", course, OptionTestUtils.createMockedOptions(7, 3))
        );

        for (Task task : tasks) {
            task.getOptions().forEach(option -> option.setTask(task));
        }

        course.setTasks(tasks);

        courseRepository.save(course);

        List<CourseAndTaskListItem> courses = courseRepository.findCoursesAndTasksByInstructorId(instructor.getId());

        courses.forEach(foundCourse -> {
            assertEquals(courseTitle, foundCourse.getTitle());
            assertEquals(Status.BUILDING, foundCourse.getStatus());
            assertEquals(tasks.size(), foundCourse.getTaskQuantity());
            assertNull(foundCourse.getPublishedAt());
            assertNotNull(foundCourse.getId());
        });

    }

}