package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public int getStudentCount() {
        return studentRepository.getStudentCount();
    }

    @Override
    public int getAverageAgeStudent() {
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getAllStudentNameStartWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(it -> it.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @Override
    public Double getAverageAgeStudentWithStreams() {
        return studentRepository.findAll()
                .stream()
                .collect(Collectors.averagingInt(Student::getAge));
    }

    @Override
    public void printParallel() {

        List<Student> students = studentRepository.findAll();

        printParallelName(students.get(0));
        printParallelName(students.get(1));

        new Thread(() -> {
            printParallelName(students.get(2));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printParallelName(students.get(3));
        }).start();

        new Thread(() -> {
            printParallelName(students.get(4));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printParallelName(students.get(5));
        }).start();
    }
    @Override
    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();

        printSynchronizedName(students.get(0));
        printSynchronizedName(students.get(1));

        new Thread(() -> {
            printSynchronizedName(students.get(2));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printSynchronizedName(students.get(3));
        }).start();

        new Thread(() -> {
            printSynchronizedName(students.get(4));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printSynchronizedName(students.get(5));
        }).start();
    }

    private void printParallelName(Student student) {
        System.out.println(Thread.currentThread().getName() + ": " + student.getName());
    }

    private synchronized void printSynchronizedName(Student student) {
        System.out.println(Thread.currentThread().getName() + ": " + student.getName());
    }
}
