package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.NotFoundException;
import br.com.alura.AluraFake.util.UserIsNotInstructorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
