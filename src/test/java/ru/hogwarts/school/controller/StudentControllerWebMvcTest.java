package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.AvatarService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private AvatarService avatarService;

    @Test
    void shouldCreateStudent() throws Exception {
        //given
        Long studentId = 1L;
        Student student = new Student("Ivan", 25);
        Student savedStudent = new Student("Ivan", 25);
        savedStudent.setId(studentId);

        when(studentService.create(student)).thenReturn(savedStudent);

        //when
        ResultActions perform = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        );

        //then
        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void shouldReadStudent() throws Exception {
        //given
        Long studentId = 1L;
        Student student = new Student("Ivan", 25);

        when(studentService.read(studentId)).thenReturn(student);

        //when
        ResultActions perform = mockMvc.perform(
                get("/students/{id}", studentId)
        );

        //then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        //given
        Long studentId = 1L;
        Student student = new Student("Ivan", 25);

        when(studentService.update(studentId, student)).thenReturn(student);

        //when
        ResultActions perform = mockMvc.perform(
                put("/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))

        );

        //then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

}
