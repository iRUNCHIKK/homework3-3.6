package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void clearDatabase() {
        facultyRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        //given
        Faculty faculty = new Faculty("name", "color");

        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculties",
                faculty,
                Faculty.class
        );

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        org.assertj.core.api.Assertions.assertThat(actualFaculty.getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    void shouldReadFaculty() {
        //given
        Faculty faculty = new Faculty("name", "color");
        faculty = facultyRepository.save(faculty);

        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculties/" + faculty.getId(),
                Faculty.class
        );

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getId(), faculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }

    @Test
    void shouldUpdateFaculty() {
        //given
        Faculty faculty = new Faculty("name", "color");
        faculty = facultyRepository.save(faculty);

        Faculty facultyForUpdate = new Faculty("newName", "newColor");

        //when
        HttpEntity<Faculty> entity = new HttpEntity<>(facultyForUpdate);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculties/" + faculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class
        );

        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getId(), faculty.getId());
        assertEquals(actualFaculty.getName(), facultyForUpdate.getName());
        assertEquals(actualFaculty.getColor(), facultyForUpdate.getColor());
    }
}
