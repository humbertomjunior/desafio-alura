package br.com.alura.AluraFake.task.request;

import br.com.alura.AluraFake.validation.NoStatementAsOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoStatementAsOption(message = "AAAAAAAAAAAAAAAAAAAAa")
public abstract class SampleOptionsTask extends SampleTask {

    public abstract List<OptionRequest> getOptions();

}
