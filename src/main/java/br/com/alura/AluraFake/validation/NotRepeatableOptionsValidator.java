package br.com.alura.AluraFake.validation;

import br.com.alura.AluraFake.task.request.OptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotRepeatableOptionsValidator implements ConstraintValidator<NotRepeatableOptions, List<OptionRequest>> {

    @Override
    public boolean isValid(List<OptionRequest> options, ConstraintValidatorContext context) {
        long distinctCount = options.stream()
                .map(OptionRequest::getOption)
                .distinct()
                .count();
        return distinctCount == options.size();
    }

}
