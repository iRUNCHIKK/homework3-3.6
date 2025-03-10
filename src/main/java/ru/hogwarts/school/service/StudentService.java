package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student read(Long studentId);

    Student update(Long studentId, Student student);

    void delete(Long studentId);

    List<Student> getAllByAge(int age);
}
