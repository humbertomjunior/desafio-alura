package br.com.alura.AluraFake.course;

import lombok.Builder;

import java.util.List;

@Builder
public class InstructorCoursesDTO {

    private final List<Course> courses;

    private final Integer coursesQuantity;

}
