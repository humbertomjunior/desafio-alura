package br.com.alura.AluraFake.user;

public class UserTestUtils {

    public static void setId(User user, Long id) {
        user.setId(id);
    }

    public static User createMockInstructor(String name, String email) {
        return new User(name, email, Role.INSTRUCTOR);
    }

    public static User createMockStudent(String name, String email) {
        return new User(name, email, Role.STUDENT);
    }

}
