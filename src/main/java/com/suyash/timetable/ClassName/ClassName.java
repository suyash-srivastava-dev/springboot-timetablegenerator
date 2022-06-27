package com.suyash.timetable.ClassName;

import com.suyash.timetable.Teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@RestControllerAdvice
public class ClassName implements Serializable {
    @Id
    private String classValue;
    private int strength;
    @OneToMany(mappedBy = "classname")
    private Set<Teacher> teacherSet;
}
