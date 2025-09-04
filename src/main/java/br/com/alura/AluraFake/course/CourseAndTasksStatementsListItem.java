package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CourseAndTasksStatementsListItem {
    private Course course;
    private List<Task> tasks;

    public CourseAndTasksStatementsListItem(Course course, Task task) {
        this.course = course;
        this.tasks = Collections.singletonList(task);
    }
}
