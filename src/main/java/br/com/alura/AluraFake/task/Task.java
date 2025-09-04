package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Task")
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String statement;

    @Setter
    @Column(name = "task_order")
    private Integer order;

    @Setter
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "task")
    private List<Option> options;

    @ManyToOne
    private Course course;

    @Builder
    public Task(Long id, String statement, Integer order, TaskType type, List<Option> options, Course course) {
        this.id = id;
        this.statement = statement;
        this.order = order;
        this.type = type;
        this.options = new ArrayList<>();
        this.course = course;
    }

    public void addOption(Option option) {
        if (options == null)
            options = new ArrayList<>();
        this.options.add(option);
        option.setTask(this);
    }

}
