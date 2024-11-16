package com.example.groupId.repository;

import com.example.groupId.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    List<Student> findByFacultyId(long id);

    @Query(value = "SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findLastFiveStudents();

    @Query("SELECT AVG(s.age) FROM Student s")
    double getStudentsAverageAge();
}