package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {;
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElse(null);
    }

    @Override
    public Faculty update(Long facultyId, Faculty faculty) {
        Faculty facultyFromDb = facultyRepository.findById(facultyId)
                .orElseThrow(IllegalAccessError::new);
        facultyFromDb.setName(faculty.getName());
        facultyFromDb.setColor(faculty.getColor());

        return facultyRepository.save(facultyFromDb);
    }

    @Override
    public void delete(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public List<Faculty> getAllByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .toList();
    }

    @Override
    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public String getLongestFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
