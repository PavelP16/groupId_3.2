package com.example.groupId.service.impl;

import com.example.groupId.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student removeStudent(long id);

    Student findStudent(long id);

    void updateStudent(long id, Student studentForUpdate);

    List<Student> findAllByAge(int age);
}
