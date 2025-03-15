package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void clearDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        //given
        Student student = new Student("name", 20);

        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/students",
                student,
                Student.class
        );

        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), student.getName());
        org.assertj.core.api.Assertions.assertThat(actualStudent.getAge()).isEqualTo(student.getAge());
    }

    @Test
    void shouldReadStudent() {
        //given
        Student student = new Student("name", 20);
        student = studentRepository.save(student);

        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/students/" + student.getId(),
                Student.class
        );

        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldUpdateStudent() {
        //given
        Student student = new Student("name", 20);
        student = studentRepository.save(student);

        Student studentForUpdate = new Student("newName", 20);

        //when
        HttpEntity<Student> entity = new HttpEntity<>(studentForUpdate);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/students/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class
        );

        //then
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), studentForUpdate.getName());
        assertEquals(actualStudent.getAge(), studentForUpdate.getAge());
    }
}
