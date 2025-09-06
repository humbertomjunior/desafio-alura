package br.com.alura.AluraFake.user;

public class UserTestUtils {

    public static User createMockInstructor(String name, String email) {
        return new User(name, email, Role.INSTRUCTOR);
    }

}
