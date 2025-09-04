package br.com.alura.AluraFake.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TaskOption")
@ToString
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    @Column(name = "option_description")
    private String option;

    @Column(name = "is_correct")
    @JsonProperty("isCorrect")
    private boolean isCorrect;

    @Builder
    public Option(Long id, Task task, String option, boolean isCorrect) {
        this.id = id;
        this.task = task;
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public static OptionBuilder getBuilder(Option option) {
        return Option.builder()
                .id(option.getId())
                .option(option.getOption())
                .task(option.getTask())
                .isCorrect(option.isCorrect());
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @JsonGetter("isCorrect")
    public boolean isCorrect() {
        return isCorrect;
    }

    @JsonSetter("isCorrect")
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
