package com.example.groupId.controller;

import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import com.example.groupId.service.impl.FacultyService;


import org.junit.jupiter.api.Test;

import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

import static java.nio.file.Paths.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.http.MediaType;


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








    @Test
    public void testGetStudentsByFacultyId_success() throws Exception {
        long facultyId = 1L;
        List<Student> students = List.of(new Student(1, "John Doe", facultyId));

        when(facultyService.findStudentsByFacultyId(facultyId)).thenReturn(students);

        ConsoleIOContext.AllSuggestionsCompletionTask mockMvc;
        mockMvc.perform(get("/1/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }

    @Test
    public void testGetStudentsByFacultyId_notFound() throws Exception {
        long facultyId = 2L;
        when(facultyService.findStudentsByFacultyId(facultyId)).thenReturn(Collections.emptyList());

        ConsoleIOContext.AllSuggestionsCompletionTask mockMvc;
        mockMvc.perform(get("/2/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddFaculty_success() throws Exception {
        Faculty faculty = new Faculty(1, "Science", "Blue");
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
    }

    @Test
    public void testAddFaculty_badRequest() throws Exception {
        Faculty faculty = new Faculty(); // Empty Faculty object simulates invalid input

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFaculty_success() throws Exception {
        long facultyId = 1L;
        Faculty faculty = new Faculty(facultyId, "Science", "Blue");
        when(facultyService.removeFaculty(facultyId)).thenReturn(faculty);

        mockMvc.perform(delete("/{id}/remove", facultyId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
    }

    @Test
    public void testRemoveFaculty_notFound() throws Exception {
        long facultyId = 2L;
        when(facultyService.removeFaculty(facultyId)).thenReturn(null);

        mockMvc.perform(delete("/{id}/remove", facultyId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindFaculty_success() throws Exception {
        long facultyId = 1L;
        Faculty faculty = new Faculty(facultyId, "Science", "Blue");
        when(facultyService.findFaculty(facultyId)).thenReturn(faculty);

        mockMvc.perform(get("/{id}/get", facultyId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
    }

    @Test
    public void testFindFaculty_notFound() throws Exception {
        long facultyId = 2L;
        when(facultyService.findFaculty(facultyId)).thenReturn(null);

        mockMvc.perform(get("/{id}/get", facultyId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFaculty_success() throws Exception {
        long facultyId = 1L;
        Faculty updatedFaculty = new Faculty(facultyId, "Arts", "Red");
        doNothing().when(facultyService).updateFaculty(eq(facultyId), any(Faculty.class));

        mockMvc.perform(put("/{id}/update", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFaculty)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFaculty_notFound() throws Exception {
        long facultyId = 2L;
        Faculty updatedFaculty = new Faculty(facultyId, "Arts", "Red");
        doThrow(new ResourceNotFoundException()).when(facultyService).updateFaculty(eq(facultyId), any(Faculty.class));

        mockMvc.perform(put("/{id}/update", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFaculty)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllByColor_success() throws Exception {
        String color = "Blue";
        List<Faculty> faculties = List.of(new Faculty(1, "Science", color));
        when(facultyService.getAllByColor(color)).thenReturn(faculties);

        mockMvc.perform(get("/get/by-color").param("color", color))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculties)));
    }

    @Test
    public void testGetAllByColor_noResults() throws Exception {
        String color = "Green";
        when(facultyService.getAllByColor(color)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/get/by-color").param("color", color))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetFacultyByColorOrName_success() throws Exception {
        String color = "Blue";
        String name = "Science";
        List<Faculty> faculties = List.of(new Faculty(1, name, color));
        when(facultyService.getFacultyByColorOrName(color, name)).thenReturn(faculties);

        mockMvc.perform(get("/get/by-color-or-name")
                        .param("color", color)
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculties)));
    }

    @Test
    public void testGetFacultyByColorOrName_noResults() throws Exception {
        String color = "Red";
        String name = "Arts";
        when(facultyService.getFacultyByColorOrName(color, name)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/get/by-color-or-name")
                        .param("color", color)
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
