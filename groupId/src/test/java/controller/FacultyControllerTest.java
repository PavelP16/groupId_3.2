package controller;

import com.example.groupId.controller.FacultyController;
import com.example.groupId.model.Faculty;
import com.example.groupId.model.Student;
import com.example.groupId.service.impl.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddAndRemoveFaculty() {
        Faculty faculty = new Faculty("Physics", "Blue");
        ResponseEntity<Faculty> responseAdd = restTemplate.postForEntity("/faculty/add", faculty, Faculty.class);
        assertThat(responseAdd.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty addedFaculty = responseAdd.getBody();
        assertThat(addedFaculty).isNotNull();
        assertThat(addedFaculty.getId()).isNotNull();

        restTemplate.delete("/faculty/{id}/remove", addedFaculty.getId());

        ResponseEntity<Faculty> responseRemove = restTemplate.getForEntity("/faculty/{id}/get", Faculty.class, addedFaculty.getId());
        assertThat(responseRemove.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetAllByColor() {
        ResponseEntity<List<Faculty>> response = restTemplate.getForEntity("/faculty/get/by-color?color=Blue", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Faculty> faculties = response.getBody();
        assertThat(faculties).isNotNull();
        assertThat(faculties).isNotEmpty();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void testAddFaculty() throws Exception {
        Faculty faculty = new Faculty("Engineering", "Red");
        given(facultyService.addFaculty(faculty)).willReturn(faculty);

        mockMvc.perform(post("/faculty/add")
                        .contentType("application/json")
                        .content("{\"name\":\"Engineering\",\"color\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    public void testFindFaculty() throws Exception {
        given(facultyService.findFaculty(1L)).willReturn(new Faculty(1L, "Engineering", "Red"));

        mockMvc.perform(get("/faculty/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Engineering"));
    }



    @Test
    public void testCreateFaculty_Success() throws Exception {
        // Данные входные
        String facultyJson = "{\"name\": \"Engineering\", \"department\": \"Science\"}";

        // Mocking Service Layer
        Faculty faculty = new Faculty();
        faculty.setName("Engineering");
        faculty.setDepartment("Science");
        when(facultyService.create(any(Faculty.class))).thenReturn(faculty);

        // Вызов и проверка
        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    public void testCreateFaculty_MissingFields() throws Exception {
        String facultyJson = "{\"name\": \"\"}"; // Отсутствует поле department

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFaculty_DuplicateName() throws Exception {
        String facultyJson = "{\"name\": \"Engineering\", \"department\": \"Science\"}";

        when(facultyService.create(any(Faculty.class)))
                .thenThrow(new DuplicateFacultyException("Faculty already exists"));

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyJson))
                .andExpect(status().isConflict());
    }




    @Test
    public void testGetStudentsByFacultyId() throws Exception {
        List<Student> students = Arrays.asList(new Student(1L, "John"), new Student(2L, "Jane"));
        when(facultyService.findStudentsByFacultyId(1L)).thenReturn(students);

        mockMvc.perform(get("/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(students.size()))
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    public void testAddFaculty() throws Exception {
        Faculty faculty = new Faculty("Science", "Blue");
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        ObjectMapper objectMapper;
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    public void testRemoveFaculty() throws Exception {
        Faculty faculty = new Faculty("Arts", "Green");
        when(facultyService.removeFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(delete("/1/remove"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arts"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void testFindFaculty() throws Exception {
        Faculty faculty = new Faculty("Math", "Yellow");
        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"))
                .andExpect(jsonPath("$.color").value("Yellow"));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty("Physics", "White");

        mockMvc.perform(put("/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk());

        verify(facultyService).updateFaculty(eq(1L), any(Faculty.class));
    }

    @Test
    public void testGetAllByColor() throws Exception {
        List<Faculty> faculties = Arrays.asList(new Faculty("Music", "Red"));
        when(facultyService.getAllByColor("Red")).thenReturn(faculties);

        mockMvc.perform(get("/get/by-color")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(faculties.size()))
                .andExpect(jsonPath("$[0].name").value("Music"));
    }

    @Test
    public void testGetFacultyByColorOrName() throws Exception {
        List<Faculty> faculties = Arrays.asList(new Faculty("Art", "Purple"));
        when(facultyService.getFacultyByColorOrName("Purple", "Art")).thenReturn(faculties);

        mockMvc.perform(get("/get/by-color-or-name")
                        .param("color", "Purple")
                        .param("name", "Art"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(faculties.size()))
                .andExpect(jsonPath("$[0].name").value("Art"));
    }


}


