package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int from, int to);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int getStudentCount();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    int getAverageAgeStudent();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();


}
