package br.com.alura.AluraFake.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CourseAndTaskListItem {

    private Long id;
    private String title;
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime publishedAt;
    private Long taskQuantity;

}
