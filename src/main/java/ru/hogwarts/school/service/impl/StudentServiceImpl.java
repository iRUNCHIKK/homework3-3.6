package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("Creating student with data: {}", student);
        if (student == null) {
            logger.error("Cannot create student: student data is null");
            throw new IllegalArgumentException("Student data cannot be null");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long studentId) {
        logger.info("Was invoked method for read student");
        logger.debug("Reading student with id: {}", studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            logger.error("There is not student with id = " + studentId);
            logger.warn("Attempt to read non-existent student with id: {}", studentId);
        }
        return student;
    }

    @Override
    public Student update(Long studentId, Student student) {
        logger.info("Was invoked method for update student");
        logger.debug("Updating student with id: {} and data: {}", studentId, student);
        try {
            Student studentFromDb = studentRepository.findById(studentId)
                    .orElseThrow(IllegalArgumentException::new);
            studentFromDb.setName(student.getName());
            studentFromDb.setAge(student.getAge());
            return studentRepository.save(studentFromDb);
        } catch (IllegalArgumentException e) {
            logger.error("There is not student with id = " + studentId);
            logger.warn("Attempt to update non-existent student with id: {}", studentId);
            throw e;
        }
    }

    @Override
    public void delete(Long studentId) {
        logger.info("Was invoked method for delete student");
        logger.debug("Deleting student with id: {}", studentId);
        try {
            if (!studentRepository.existsById(studentId)) {
                logger.error("There is not student with id = " + studentId);
                logger.warn("Attempt to delete non-existent student with id: {}", studentId);
            }
            studentRepository.deleteById(studentId);
        } catch (Exception e) {
            logger.error("Error while deleting student: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Student> getAllByAge(int age) {
        logger.info("Was invoked method for get all students by age");
        logger.debug("Getting all students with age: {}", age);
        List<Student> students = studentRepository.findAll()
                .stream()
                .filter(it -> it.getAge() == age)
                .toList();
        if (students.isEmpty()) {
            logger.warn("No students found with age: {}", age);
        }
        return students;
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between");
        logger.debug("Finding students with age between {} and {}", min, max);
        if (min > max) {
            logger.error("Min age {} is greater than max age {}", min, max);
            logger.warn("Invalid age range provided: min={}, max={}", min, max);
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public int getStudentCount() {
        logger.info("Was invoked method for get student count");
        logger.debug("Getting total student count");
        return studentRepository.getStudentCount();
    }

    @Override
    public int getAverageAgeStudent() {
        logger.info("Was invoked method for get average age student");
        logger.debug("Calculating average age of students");
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        logger.debug("Retrieving last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getAllStudentNameStartWithA() {
        logger.info("Was invoked method for get all student names start with A");
        logger.debug("Filtering students with names starting with 'A'");
        List<String> names = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(it -> it.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
        if (names.isEmpty()) {
            logger.warn("No student names starting with 'A' were found");
        }
        return names;
    }

    @Override
    public Double getAverageAgeStudentWithStreams() {
        logger.info("Was invoked method for get average age student with streams");
        logger.debug("Calculating average age using streams");
        return studentRepository.findAll()
                .stream()
                .collect(Collectors.averagingInt(Student::getAge));
    }

    @Override
    public void printParallel() {
        logger.info("Was invoked method for print parallel");
        logger.debug("Starting parallel printing of student names");
        try {
            List<Student> students = studentRepository.findAll();
            if (students.size() < 6) {
                logger.error("Not enough students to perform parallel printing");
                logger.warn("Need at least 6 students, but found only {}", students.size());
                return;
            }

            printParallelName(students.get(0));
            printParallelName(students.get(1));
            new Thread(() -> {
                printParallelName(students.get(2));
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
                printParallelName(students.get(3));
            }).start();
            new Thread(() -> {
                printParallelName(students.get(4));
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
                printParallelName(students.get(5));
            }).start();
        } catch (Exception e) {
            logger.error("Error in printParallel method: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void printSynchronized() {
        logger.info("Was invoked method for print synchronized");
        logger.debug("Starting synchronized printing of student names");
        try {
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                logger.error("No students found for synchronized printing");
                logger.warn("Student list is empty");
                return;
            }
            printSynchronizedName(students.get(0));
        } catch (Exception e) {
            logger.error("Error in printSynchronized method: {}", e.getMessage());
            throw e;
        }
    }
    private void printParallelName(Student student) {
        logger.debug("Printing name in parallel: {}", student.getName());
        System.out.println(student.getName());
    }

    private synchronized void printSynchronizedName(Student student) {
        logger.debug("Printing name in synchronized method: {}", student.getName());
        System.out.println(student.getName());
    }
}
