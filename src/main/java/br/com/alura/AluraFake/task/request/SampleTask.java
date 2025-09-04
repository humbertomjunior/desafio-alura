package br.com.alura.AluraFake.task.request;

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
    protected String statement;
    protected Integer order;

}
