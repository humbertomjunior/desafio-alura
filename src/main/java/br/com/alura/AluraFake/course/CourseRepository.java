package br.com.alura.AluraFake.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT new br.com.alura.AluraFake.course.CourseAndTaskListItem(c.id, c.title, c.status, c.publishedAt, COUNT(t)) " +
            "FROM Course c LEFT JOIN c.tasks t " +
            "WHERE c.instructor.id = :instructorId " +
            "GROUP BY c.id, c.title, c.status, c.publishedAt " + // Agrupe por todas as colunas que não são agregadas
            "ORDER BY c.createdAt DESC")
    List<CourseAndTaskListItem> findCoursesAndTasksByInstructorId(Long instructorId);

    @Query("SELECT new br.com.alura.AluraFake.course.CourseAndTasksStatementsListItem(c, t) " +
            "FROM Course c " +
            "LEFT JOIN FETCH c.tasks t " +
            "WHERE c.id = :courseId")
    Optional<CourseAndTasksStatementsListItem> findCoursesAndTasksStatementsByCourseId(Long courseId);

}
