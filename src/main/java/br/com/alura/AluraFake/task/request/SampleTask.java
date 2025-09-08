package br.com.alura.AluraFake.task.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O courseId não pode ser null.")
    protected Long courseId;

    @Size(min = 4, max = 255, message = "O enunciado (statement) deve ter no mínimo 4 e no máximo 255 caracteres")
    @NotNull(message = "O statement não pode ser null.")
    protected String statement;

    @Min(value = 1, message = "A ordem deve ser um número inteiro positivo.")
    @NotNull(message = "A order não pode ser null.")
    protected Integer order;

}
