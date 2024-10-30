package com.example.groupId.repository;


import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT COUNT(s) FROM Student s")
    long getStudentsCount();

    @Query("SELECT AVG(s.age) FROM Student s")
    double getStudentsAverageAge();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudents();

    List<Student> findByAgeBetween(int ageMin, int ageMax);

   List <Student> findByFacultyId(long id);


 
}
