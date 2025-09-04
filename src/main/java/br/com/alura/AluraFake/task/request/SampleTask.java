package br.com.alura.AluraFake.task.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SampleTask {

    protected Long courseId;

    @Size(min = 4, max = 255, message = "O enunciado (statement) deve ter no mínimo 4 e no máximo 255 caracteres")
    protected String statement;

    @Min(value = 0, message = "A ordem deve ser um número inteiro positivo.")
    protected Integer order;

}
