package controller;

import com.example.groupId.GroupIdApplication;
import com.example.groupId.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GroupIdApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddFaculty() {
        Faculty faculty = new Faculty("Engineering", "Red");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty/add", faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Engineering");
    }

    @Test
    public void testFindFaculty() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/1/get", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(1);
    }


}