package br.com.alura.AluraFake.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT new br.com.alura.AluraFake.course.CourseAndTaskListItem(c.id, c.title, c.status, c.publishedAt, COUNT(t)) " +
            "FROM Course c LEFT JOIN c.tasks t " +
            "WHERE c.instructor.id = :instructorId " +
            "GROUP BY c.id, c.title, c.status, c.publishedAt " +
            "ORDER BY c.createdAt DESC")
    List<CourseAndTaskListItem> findCoursesAndTasksByInstructorId(Long instructorId);

}
