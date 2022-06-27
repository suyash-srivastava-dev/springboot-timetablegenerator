package com.suyash.timetable.Teacher;

import com.suyash.timetable.ClassName.ClassName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    boolean existsBySubject(String subject);

    List<Teacher> findByClassname(ClassName classValue);

    boolean existsByName(String name);

    List<Teacher> findAllByName(String name);

    List<Teacher> findAllByClassname(ClassName item);
}
