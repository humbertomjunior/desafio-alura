package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserTestUtils;

import java.awt.event.WindowStateListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseTestUtils {

    public static void setCourseId(Course course, Long id) {
        course.setId(id);
    }

    public static void setTasks(Course course, List<Task> tasks) {
        course.setTasks(tasks);
    }

    public static Course createMockedCourse(User instructor, List<Task> tasks) {
        if (instructor == null) instructor = UserTestUtils.createMockInstructor("Instrutor", "instrutor@email.com");
        final var course = new Course("Curso 1", "Curso mockado número 1", instructor);
        setCourseId(course, 1L);
        return course;
    }

    public static Course createMockedCourse() {
        final var instructor = UserTestUtils.createMockInstructor("Instrutor", "instrutor@email.com");
        final var course = new Course("Curso 1", "Curso mockado número 1", instructor);
        setCourseId(course, 1L);
        return course;
    }

    public static Course createMockedCourse(List<Task> tasks) {
        final var instructor = UserTestUtils.createMockInstructor("Instrutor", "instrutor@email.com");
        final var course = new Course("Curso 1", "Curso mockado número 1", instructor);
        setCourseId(course, 1L);
        setTasks(course, tasks);
        return course;
    }

    public static CourseAndTaskListItem createMockCourseAndTaskListItem(String title, Status status, LocalDateTime publishedAt, Long taskQuantity) {
        return CourseAndTaskListItem.builder()
                .id(1L)
                .title(title)
                .status(status)
                .publishedAt(publishedAt)
                .taskQuantity(taskQuantity)
                .build();
    }

    public static CourseAndTaskListItem createMockCourseAndTaskListItem(Course course) {
        final var taskQuantity = (long) course.getTasks().size();
        return CourseAndTaskListItem.builder()
                .id(course.getId())
                .title(course.getTitle())
                .status(course.getStatus())
                .publishedAt(course.getPublishedAt())
                .taskQuantity(taskQuantity)
                .build();
    }

    public static List<CourseAndTaskListItem> createMockCoursesAndTasksListItem(List<Course> courses) {
        List<CourseAndTaskListItem> mocks = new ArrayList<>();
        courses.forEach(course -> {
            final var taskQuantity = (long) course.getTasks().size();
            mocks.add(CourseAndTaskListItem.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .status(course.getStatus())
                    .publishedAt(course.getPublishedAt())
                    .taskQuantity(taskQuantity)
                    .build());
        });
        return mocks;
    }

}