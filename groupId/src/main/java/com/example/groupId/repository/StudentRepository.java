package com.example.groupId.repository;


import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int ageMin, int ageMax);

   List<Student> findByFacultyId(long id);


}
