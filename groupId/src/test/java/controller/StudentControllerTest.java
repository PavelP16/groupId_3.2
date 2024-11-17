package controller;

import com.example.groupId.controller.StudentController;
import com.example.groupId.model.Student;
import com.example.groupId.service.impl.StudentService;
import com.jayway.jsonpath.spi.json.JakartaJsonProvider;
import net.minidev.json.JSONNavi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testAddStudent() throws Exception {
        Student student = new Student("John Doe", 20);
        given(studentService.addStudent(student)).willReturn(student);

        mockMvc.perform(post("/student/add")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"age\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testFindStudent() throws Exception {
        given(studentService.findStudent(1L)).willReturn(new Student(1L, "John Doe", 20));

        mockMvc.perform(get("/student/id/get?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }


    @BeforeEach
    public void setUp() {
        List<Student> students = Arrays.asList(
                new Student("John", 20),
                new Student("Jane", 22)
        );
        Mockito.when(studentService.findAllByAge(20)).thenReturn(students);
    }

    @Test
    public void testFindAllByAge() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/get/by-age")
                        .param("age", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFindStudent() throws Exception {
        JSONNavi students;
        Mockito.when(studentService.findStudent(1L)).thenReturn(students.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/id/get")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }


}