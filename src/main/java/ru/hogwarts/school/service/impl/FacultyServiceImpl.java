package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();

    private static Long counter = 0L;

    @Override
    public Faculty create(Faculty faculty) {
        Long currentId = ++counter;
        faculty.setId(currentId);

        faculties.put(currentId, faculty);
        return faculty;
    }

    @Override
    public Faculty read(Long facultyId) {
        return faculties.get(facultyId);
    }

    @Override
    public Faculty update(Long facultyId, Faculty faculty) {
        Faculty facultyFromDb = faculties.get(facultyId);
        facultyFromDb.setName(faculty.getName());
        facultyFromDb.setColor(faculty.getColor());

        return facultyFromDb;
    }

    @Override
    public void delete(Long facultyId) {
        faculties.remove(facultyId);
    }

    @Override
    public List<Faculty> getAllByColor(String color) {
        return faculties.values()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .toList();
    }
}
