package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty read(Long facultyId);

    Faculty update(Long facultyId, Faculty faculty);

    void delete(Long facultyId);

    List<Faculty> getAllByColor(String color);
}
