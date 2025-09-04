package br.com.alura.AluraFake.task.request;

import br.com.alura.AluraFake.task.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SampleOptionsTask extends SampleTask {

    protected List<Option> options;

}
