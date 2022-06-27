package com.suyash.timetable.Teacher;

import com.suyash.timetable.ClassName.ClassList;
import lombok.Data;

import java.util.List;

@Data
public class TeacherList {
        private Long id;
        private String name;

        public void setGender(Teacher.Gender gender) {
                this.gender=gender;
        }

        private enum Gender{
                MALE,FEMALE,OTHER
        }
       private Teacher.Gender gender;
        private String subject;
        private List<ClassList> classList;

}
