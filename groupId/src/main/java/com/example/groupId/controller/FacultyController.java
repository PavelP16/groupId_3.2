package com.example.groupId.controller;

import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import com.example.groupId.service.impl.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByFacultyId(@PathVariable("id") long id) {
        return facultyService.findStudentsByFacultyId(id);
    }

    @PostMapping("/add")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);

    }

    @DeleteMapping("/{id}/remove")
    public Faculty removeFaculty(@PathVariable("id") long id) {
        return facultyService.removeFaculty(id);

    }

    @GetMapping("/{id}/get")
    public Faculty findFaculty(@PathVariable("id") long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping("/{id}/update")
    public void updateFaculty(@PathVariable("id") long id,
                              @RequestBody Faculty faculty) {
        facultyService.updateFaculty(id, faculty);

    }

    @GetMapping("/get/by-color")
    public List<Faculty> getAllByColor(@RequestParam("color") String color) {
        return facultyService.getAllByColor(color);
    }

    @GetMapping("/get/by-color-or-name")
    public List<Faculty> getFacultyByColorOrName(@RequestParam ("color") String color,
                                                 @RequestParam("name")String name) {
        return facultyService.getFacultyByColorOrName(color,name);
    }

}
