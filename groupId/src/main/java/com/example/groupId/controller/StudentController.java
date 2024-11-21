package com.example.groupId.controller;

import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import com.example.groupId.service.impl.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/id/get")
    public Student findStudent(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    @DeleteMapping("/{id}/remove")
    public Student removeStudent(@PathVariable long id) {
        return studentService.removeStudent(id);
    }

    @PutMapping("/{id}/update")
    public void updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @GetMapping("/get/by-age")
    public List<Student> findAllByAge(@RequestParam("age") int age) {
        return studentService.findAllByAge(age);
    }

    @GetMapping("/get/by-age-between")
    public List<Student> findByAgeBetween(@RequestParam("ageMin")int ageMin,
                                             @RequestParam("ageMax")int ageMax) {
        return studentService.findByAgeBetween(ageMin,ageMax);
    }

    @GetMapping("/{id}/get/faculty")
    public Faculty findFacultyByStudentId(@PathVariable("id") long id){
        return studentService.findFacultyByStudentId(id);
    }

}
