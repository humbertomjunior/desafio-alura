package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.OptionTestUtils;
import br.com.alura.AluraFake.task.TaskTestUtils;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.user.UserTestUtils;
import br.com.alura.AluraFake.util.exception.CourseMissesTaskTypeException;
import br.com.alura.AluraFake.util.exception.CourseTasksOrdersNotInSequenceException;
import br.com.alura.AluraFake.util.exception.NotFoundException;
import br.com.alura.AluraFake.util.exception.UserIsNotInstructorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void getCoursesByInstructorId__should_return_list_of_courses() {
        final var instructorId = 1L;
        final var courseName = "Curso 1";
        final var courseStatus = Status.BUILDING;
        final var coursePublishedAt = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        final var courseTaskQuantity = 5L;

        User instructor = UserTestUtils.createMockInstructor("user1", "user1@mail.com");
        UserTestUtils.setId(instructor, instructorId);

        final var mockedCourses = List.of(CourseTestUtils.createMockCourseAndTaskListItem(courseName, courseStatus, coursePublishedAt, courseTaskQuantity));

        when(userRepository.findById(instructor.getId())).thenReturn(Optional.of(instructor));
        when(courseRepository.findCoursesAndTasksByInstructorId(instructor.getId())).thenReturn(mockedCourses);

        List<CourseAndTaskListItem> courses = courseService.getCoursesByInstructorId(instructorId);

        for (int i = 0; i < courses.size(); i++) {
            assertEquals(courses.get(i).getId(), mockedCourses.get(i).getId());
            assertEquals(courses.get(i).getStatus(), mockedCourses.get(i).getStatus());
            assertEquals(courses.get(i).getTitle(), mockedCourses.get(i).getTitle());
            assertEquals(courses.get(i).getPublishedAt(), mockedCourses.get(i).getPublishedAt());
            assertEquals(courses.get(i).getTaskQuantity(), mockedCourses.get(i).getTaskQuantity());
        }

    }

    @Test
    void getCoursesByInstructorId__should_return_list_of_more_than_one_course() {
        final var instructorId = 1L;
        final var courseName = "Curso";
        final var courseStatus = Status.BUILDING;
        final var coursePublishedAt = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        final var courseTaskQuantity = 5L;

        User instructor = UserTestUtils.createMockInstructor("user1", "user1@mail.com");
        UserTestUtils.setId(instructor, instructorId);

        final var mockedCourses = List.of(
                CourseTestUtils.createMockCourseAndTaskListItem(courseName.concat(" 1"), courseStatus, coursePublishedAt.plusHours(1), courseTaskQuantity),
                CourseTestUtils.createMockCourseAndTaskListItem(courseName.concat(" 2"), courseStatus, coursePublishedAt.plusHours(2), courseTaskQuantity),
                CourseTestUtils.createMockCourseAndTaskListItem(courseName.concat(" 3"), courseStatus, coursePublishedAt.plusHours(3), courseTaskQuantity)

        );

        when(userRepository.findById(instructor.getId())).thenReturn(Optional.of(instructor));
        when(courseRepository.findCoursesAndTasksByInstructorId(instructor.getId())).thenReturn(mockedCourses);

        List<CourseAndTaskListItem> courses = courseService.getCoursesByInstructorId(instructorId);

        for (int i = 0; i < courses.size(); i++) {
            assertEquals(courses.get(i).getId(), mockedCourses.get(i).getId());
            assertEquals(courses.get(i).getStatus(), mockedCourses.get(i).getStatus());
            assertEquals(courses.get(i).getTitle(), mockedCourses.get(i).getTitle());
            assertEquals(courses.get(i).getPublishedAt(), mockedCourses.get(i).getPublishedAt());
            assertEquals(courses.get(i).getTaskQuantity(), mockedCourses.get(i).getTaskQuantity());
        }

    }

    @Test
    void getCoursesByInstructorId__should_return_empty_list() {
        final var instructorId = 1L;

        User instructor = UserTestUtils.createMockInstructor("user1", "user1@mail.com");
        UserTestUtils.setId(instructor, instructorId);

        when(userRepository.findById(instructor.getId())).thenReturn(Optional.of(instructor));
        when(courseRepository.findCoursesAndTasksByInstructorId(instructor.getId())).thenReturn(Collections.EMPTY_LIST);
        List<CourseAndTaskListItem> courses = courseService.getCoursesByInstructorId(instructorId);
        assertAll(
                () -> assertEquals(courses, Collections.EMPTY_LIST)
        );
    }

    @Test
    void getCoursesByInstructorId__should_throw_not_found_exception() {
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotFoundException.class, () -> courseService.getCoursesByInstructorId(Mockito.anyLong()));
    }

    @Test
    void getCoursesByInstructorId__should_throw_user_is_not_instructor_exception() {
        final var instructorId = 1L;

        User notInstructor = UserTestUtils.createMockStudent("user1", "user1@mail.com");
        UserTestUtils.setId(notInstructor, instructorId);
        when(userRepository.findById(notInstructor.getId())).thenReturn(Optional.of(notInstructor));
        assertThrows(UserIsNotInstructorException.class, () -> courseService.getCoursesByInstructorId(notInstructor.getId()));
    }

    @Test
    void publishCourse__should_publish_course() {
        final var courseId = 1L;
        final var baseStatement = "Statement number #%s";
        final var course = CourseTestUtils.createMockedCourse();
        CourseTestUtils.setCourseId(course, courseId);

        final var options = OptionTestUtils.createMockedOptions(3, 1);

        final var tasks = List.of(
                TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 1, baseStatement.formatted(1), course),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 2, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 3, baseStatement.formatted(1), course, options)
        );

        CourseTestUtils.setTasks(course, tasks);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        this.courseService.publishCourse(courseId);

        assertNotEquals(null, course.getPublishedAt());
        assertEquals(Status.PUBLISHED, course.getStatus());

        verify(courseRepository, times(1)).save(course);

    }

    @Test
    void publishCourse__should_throw_not_found_exception() {
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NotFoundException.class, () -> courseService.publishCourse(1L));
    }

    @Test
    void publishCourse__should_throw_course_tasks_orders_not_in_sequence_exception_first_order_different_than_one() {
        final var courseId = 1L;
        final var baseStatement = "Statement number #%s";
        final var course = CourseTestUtils.createMockedCourse();
        CourseTestUtils.setCourseId(course, courseId);

        final var options = OptionTestUtils.createMockedOptions(3, 1);

        final var tasks = List.of(
                TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 2, baseStatement.formatted(1), course),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 3, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 5, baseStatement.formatted(1), course, options)
        );

        CourseTestUtils.setTasks(course, tasks);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(CourseTasksOrdersNotInSequenceException.class, () -> this.courseService.publishCourse(courseId));

    }

    @Test
    void publishCourse__should_throw_course_tasks_orders_not_in_sequence_exception() {
        final var courseId = 1L;
        final var baseStatement = "Statement number #%s";
        final var course = CourseTestUtils.createMockedCourse();
        CourseTestUtils.setCourseId(course, courseId);

        final var options = OptionTestUtils.createMockedOptions(3, 1);

        final var tasks = List.of(
                TaskTestUtils.createMockedTask(TaskType.OPEN_TEXT, 1, baseStatement.formatted(1), course),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 2, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 3, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 6, baseStatement.formatted(1), course, options)
        );

        CourseTestUtils.setTasks(course, tasks);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(CourseTasksOrdersNotInSequenceException.class, () -> this.courseService.publishCourse(courseId));

    }

    @Test
    void publishCourse__should_throw_course_misses_task_type_exception() {
        final var courseId = 1L;
        final var baseStatement = "Statement number #%s";
        final var course = CourseTestUtils.createMockedCourse();
        CourseTestUtils.setCourseId(course, courseId);

        final var options = OptionTestUtils.createMockedOptions(3, 1);

        final var tasks = List.of(
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 1, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.SINGLE_CHOICE, 2, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 3, baseStatement.formatted(1), course, options),
                TaskTestUtils.createMockedTask(TaskType.MULTIPLE_CHOICE, 4, baseStatement.formatted(1), course, options)
        );

        CourseTestUtils.setTasks(course, tasks);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(CourseMissesTaskTypeException.class, () -> this.courseService.publishCourse(courseId));

    }

}