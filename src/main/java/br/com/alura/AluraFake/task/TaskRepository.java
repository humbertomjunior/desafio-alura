package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t.statement FROM Task t WHERE t.course.id = :courseId")
    List<String> findTasksStatementsByCourseId(@Param("courseId") Long courseId);

}
