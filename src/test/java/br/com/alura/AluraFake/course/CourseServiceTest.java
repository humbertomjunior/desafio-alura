package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

}