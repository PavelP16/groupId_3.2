package com.example.groupId.service.impl;

import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student removeStudent(long id);

    Student findStudent(long id);

    void updateStudent(long id, Student studentForUpdate);

    List<Student> findAllByAge(int age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    Faculty findFacultyByStudentId(long id);

    List <Student> findByStudentId(long id);


}
