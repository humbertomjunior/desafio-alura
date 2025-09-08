package br.com.alura.AluraFake.task.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OptionRequest {

    @Length(min = 4, max = 80, message = "As alternativas devem ter no mínimo 4 e no máximo 80 caracteres.")
    @NotNull(message = "A options.option não pode ser null.")
    @Setter(AccessLevel.PROTECTED)
    private String option;

    @JsonProperty("isCorrect")
    @NotNull(message = "A options.isCorrect não pode ser null.")
    private boolean isCorrect;

    @JsonGetter("isCorrect")
    public boolean isCorrect() {
        return isCorrect;
    }

    @JsonSetter("isCorrect")
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
