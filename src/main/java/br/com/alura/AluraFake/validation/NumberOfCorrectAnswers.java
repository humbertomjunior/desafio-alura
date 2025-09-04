package br.com.alura.AluraFake.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NumberOfCorrectAnswersValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberOfCorrectAnswers {
    String message();
    int max() default -1;
    int min() default -1;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
