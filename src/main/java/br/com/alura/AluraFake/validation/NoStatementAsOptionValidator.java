package br.com.alura.AluraFake.validation;

import br.com.alura.AluraFake.task.request.OptionRequest;
import br.com.alura.AluraFake.task.request.SampleOptionsTask;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoStatementAsOptionValidator implements ConstraintValidator<NoStatementAsOption, SampleOptionsTask> {

    @Override
    public boolean isValid(SampleOptionsTask task, ConstraintValidatorContext context) {
        final var anyMatch = task.getOptions()
                .stream()
                .map(OptionRequest::getOption)
                .anyMatch(option -> option.equals(task.getStatement()));
        if (anyMatch) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("As alternativas não podem ser iguais ao enunciado da atividade.")
                    .addPropertyNode("options")
                    .addPropertyNode("option")
                    .addConstraintViolation();
        }
        return !anyMatch;
    }

}
