package com.example.groupId.service.impl;

import com.example.groupId.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentServiceImplTest {
    @Autowired
    private  StudentService studentService;

    @Test
    @DisplayName("Добавление студента")
    void addStudent() {
        Student expected = new Student("test", 18);

        //test
        Student actual = studentService.addStudent(expected);

        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Удаление студента")
    void removeStudent() {
        Student expected = new Student("test", 18);
        Student savedStudent = studentService.addStudent(expected);

        //test
        Student actual = studentService.removeStudent(savedStudent.getId());

        //check
        assertEquals(actual, expected);
        Student student = studentService.findStudent(savedStudent.getId());
        assertNull(student);

    }

    @Test
    @DisplayName("Нахождение студента")
    void findStudent() {
        Student expected = new Student("test", 18);
        Student savedStudent = studentService.addStudent(expected);

        //test
        Student actual = studentService.findStudent(savedStudent.getId());

        //check
        assertEquals(actual, expected);

    }

    @Test
    @DisplayName("Измение студента")
    void updateStudent() {
        Student student = new Student("test", 18);
        Student savedStudent = studentService.addStudent(student);
        Student expected = new Student("test2", 18);

        //test
        studentService.updateStudent(savedStudent.getId(), expected);

        //check
        Student actual = studentService.findStudent(savedStudent.getId());
        assertEquals(actual,expected);

    }

    @Test
    @DisplayName("Нахождение все студентов по возрасту")
    void findAllByAge() {
        int age=19;
        Student student = new Student("test", 18);
        Student expected = new Student("test2", age);
        Student expected2 = new Student("test3", age);
         studentService.addStudent(student);
         studentService.addStudent(expected);
         studentService.addStudent(expected2);

        //test
        List<Student> actual = studentService.findAllByAge(age);

        //check
        assertThat(actual).containsAll(List.of(expected, expected2));
    }
}