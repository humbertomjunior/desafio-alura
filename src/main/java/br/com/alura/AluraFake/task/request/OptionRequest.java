package br.com.alura.AluraFake.task.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    @Setter(AccessLevel.PROTECTED)
    private String option;

    @JsonProperty("isCorrect")
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
