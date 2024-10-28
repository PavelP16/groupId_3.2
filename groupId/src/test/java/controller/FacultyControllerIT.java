package controller;


import com.example.groupId.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllFacultiesTest(){

        ResponseEntity<Faculty> newFacultyResponse = testRestTemplate.postForEntity("http://localhost:" + "/faculty", new Faculty("Name", "Red"), Faculty.class);

        ResponseEntity<Faculty[]> response = testRestTemplate.getForEntity("http://localhost:" + "/faculty", Faculty[].class);


        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNotNull();

        Faculty[] faculties=response.getBody();
        assertThat(faculties[0].getName()).isEqualTo("Name");

    }

}
