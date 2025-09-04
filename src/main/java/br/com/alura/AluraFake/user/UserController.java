package br.com.alura.AluraFake.user;

import br.com.alura.AluraFake.course.CourseService;
import br.com.alura.AluraFake.course.CourseAndTaskListItem;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final CourseService courseService;

    public UserController(UserRepository userRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseService = courseService;
    }

    @Transactional
    @PostMapping("/user/new")
    public ResponseEntity newStudent(@RequestBody @Valid NewUserDTO newUser) {
        if(userRepository.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("email", "Email já cadastrado no sistema"));
        }
        User user = newUser.toModel();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/all")
    public List<UserListItemDTO> listAllUsers() {
        return userRepository.findAll().stream().map(UserListItemDTO::new).toList();
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<List<CourseAndTaskListItem>> getCoursesByInstructorId(@PathVariable("id") Long instructorId) {
        return ResponseEntity.ok(this.courseService.getCoursesByInstructorId(instructorId));
    }

}
