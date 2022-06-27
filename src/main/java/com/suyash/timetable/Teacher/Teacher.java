package com.suyash.timetable.Teacher;

import com.suyash.timetable.ClassName.ClassName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@RestControllerAdvice
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    protected enum Gender{
        MALE,FEMALE,OTHER
    }
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private String subject;
    @ManyToOne
    @JoinColumn(name = "classname", nullable = false)
    private ClassName classname;
}

