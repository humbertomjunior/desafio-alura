package br.com.alura.AluraFake.task.response;

import br.com.alura.AluraFake.task.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskResponse {

    private Long courseId;
    private String statement;
    private Integer order;
    private TaskType type;

}
