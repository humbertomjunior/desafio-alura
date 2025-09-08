package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Task")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
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

    public Task increaseOrder() {
        this.order++;
        return this;
    }

}
