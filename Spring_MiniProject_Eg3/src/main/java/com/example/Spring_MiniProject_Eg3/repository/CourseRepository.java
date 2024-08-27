package com.example.Spring_MiniProject_Eg3.repository;
import com.example.Spring_MiniProject_Eg3.entity.Course;
import com.example.Spring_MiniProject_Eg3.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer>
{
    @Query("SELECT c.student FROM Course c WHERE c.courseId = :courseId")
    Optional<Student> findStudentByCourseId(@Param("courseId") int courseId);


}
