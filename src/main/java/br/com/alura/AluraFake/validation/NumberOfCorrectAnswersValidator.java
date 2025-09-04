package br.com.alura.AluraFake.validation;

import br.com.alura.AluraFake.task.request.OptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NumberOfCorrectAnswersValidator implements ConstraintValidator<NumberOfCorrectAnswers, List<OptionRequest>> {

    private int max;
    private int min;

    @Override
    public void initialize(NumberOfCorrectAnswers constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List<OptionRequest> options, ConstraintValidatorContext context) {
        long correctAnswersCount = options.stream().filter(OptionRequest::isCorrect).count();
        boolean result = true;
        if (this.max >= 0)
            result = result && validateMax(correctAnswersCount);
        if (this.min >= 0)
            result = result && validateMin(correctAnswersCount);
        return result;
    }

    private boolean validateMax(long correctAnswersCount) {
        return correctAnswersCount <= this.max;
    }

    private boolean validateMin(long correctAnswersCount) {
        return correctAnswersCount >= this.min;
    }
}
