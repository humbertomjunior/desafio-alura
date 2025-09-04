package br.com.alura.AluraFake.task.response;

import br.com.alura.AluraFake.task.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatedChoiceTask extends CreatedTask {

    private List<Option> options;

}
