package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.user.User;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String title;
    private String description;
    @ManyToOne
    private User instructor;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime publishedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Task> tasks;

    @Deprecated
    public Course(){}

    public Course(String title, String description, User instructor) {
        Assert.isTrue(instructor.isInstructor(), "Usuario deve ser um instrutor");
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.status = Status.BUILDING;
    }

    public boolean areTasksOrdersSequenced() {
        final var sortedOrders = this.tasks.stream().map(Task::getOrder).sorted().toList();

        if (!sortedOrders.get(0).equals(1))
            return false;

        IntStream.range(0, sortedOrders.size()-1).allMatch(i -> sortedOrders.get(i).equals(sortedOrders.get(i+1)-1));

        return IntStream.range(0, this.tasks.size() - 1)
                .allMatch(i -> sortedOrders.get(i).equals(i + 1));
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAsPublished() {
        this.status = Status.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
    }

    public User getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
