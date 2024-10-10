package com.example.groupId.service.impl;

import com.example.groupId.exception.StudentNotFoundException;
import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import com.example.groupId.repository.FacultyRepository;
import com.example.groupId.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student removeStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
        return student;
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public void updateStudent(long id, Student studentForUpdate) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentForUpdate.setId(id);
        studentRepository.save(studentForUpdate);

    }

    @Override
    public List<Student> findAllByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    @Override
    public List<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    @Override
    public Faculty findFacultyByStudentId(long id) {
        return facultyRepository.findByStudentId(id);
    }

    @Override
    public List<Student> findByFacultyId(long id) {
        return facultyRepository.findById(id).orElseThrow().getStudents();
    }




}
