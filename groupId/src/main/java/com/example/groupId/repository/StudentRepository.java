// StudentRepository.java
package com.example.groupId.repository;

import com.example.groupId.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;



public interface StudentRepository extends JpaRepository<Student, Long> {



    List<Student> findByAgeBetween(int startAge, int endAge);


    List<Student> findByFacultyId(Long facultyId);

    List<Student> findLastFiveStudents(Pageable pageable);

    List<Student> findTop5ByOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();

    @Query("SELECT AVG(s.age) FROM Student s")
    Double getStudentsAverageAge();




}