package br.com.alura.AluraFake.user;

import br.com.alura.AluraFake.util.PasswordGeneration;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    // Por questões didáticas, a senha será armazenada em texto plano.
    private String password;

    @Deprecated
    public User() {}

    public User(String name, String email, Role role, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, Role role) {
        this(name, email, role, PasswordGeneration.generatePassword());
    }

    public boolean isInstructor() {
        return Role.INSTRUCTOR.equals(this.role);
    }

}
