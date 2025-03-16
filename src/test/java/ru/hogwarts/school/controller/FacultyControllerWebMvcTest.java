package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.AvatarService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @MockitoBean
    private AvatarService avatarService;

    @Test
    void shouldCreateFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("name", "color");
        Faculty savedFaculty = new Faculty("name", "color");
        savedFaculty.setId(facultyId);

        when(facultyService.create(faculty)).thenReturn(savedFaculty);

        //when
        ResultActions perform = mockMvc.perform(post("/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty))
        );

        //then
        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldReadFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("name", "color");

        when(facultyService.read(facultyId)).thenReturn(faculty);

        //when
        ResultActions perform = mockMvc.perform(
                get("/faculties/{id}", facultyId)
        );

        //then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void shouldUpdateFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("name", "color");

        when(facultyService.update(facultyId, faculty)).thenReturn(faculty);

        //when
        ResultActions perform = mockMvc.perform(
                put("/faculties/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty))

        );

        //then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

}
