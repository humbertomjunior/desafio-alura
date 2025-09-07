package br.com.alura.AluraFake.task.request;

import br.com.alura.AluraFake.validation.NoStatementAsOption;
import br.com.alura.AluraFake.validation.NotRepeatableOptions;
import br.com.alura.AluraFake.validation.NumberOfCorrectAnswers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceTask extends SampleOptionsTask {

    @Size(min = 2, max = 5, message = "A atividade deve ter no minimo 3 e no máximo 5 alternativas.")
    @NumberOfCorrectAnswers(min = 1, max = 1, message = "A atividade deve ter uma única alternativa correta.")
    @NotRepeatableOptions(message = "As alternativas não podem ser iguais entre si.")
    @Valid
    protected List<OptionRequest> options;

}
