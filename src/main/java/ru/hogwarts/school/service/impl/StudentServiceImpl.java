package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    @Override
    public Student update(Long studentId, Student student) {
        Student studentFromDb = studentRepository.findById(studentId)
                .orElseThrow(IllegalArgumentException::new);
        studentFromDb.setName(student.getName());
        studentFromDb.setAge(student.getAge());

        return studentRepository.save(studentFromDb);
    }

    @Override
    public void delete(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<Student> getAllByAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(it -> it.getAge() == age)
                .toList();
    }

    @Override
    public List<Student> findByAgeBetween(int from, int to) {
        return studentRepository.findByAgeBetween(from, to);
    }
}
