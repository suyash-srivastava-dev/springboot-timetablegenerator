package com.suyash.timetable.Teacher;

import com.suyash.timetable.ClassName.ClassList;
import com.suyash.timetable.ClassName.ClassNameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ClassNameRepo classNameRepo;
    public Map<String,String> createTeacher(Teacher teacher)
    {
       Map<String,String> response=new HashMap<>();
       if(!teacherRepo.existsBySubject(teacher.getSubject()) || !teacherRepo.existsByName(teacher.getName())) {
           teacherRepo.save(teacher);
           response.put("status", "201");
           response.put("details", "teacher created");
           System.out.println("Teacher added");
       }
       else {
           response.put("status","404");
           response.put("details","teacher with the same subject already exists.");
           System.out.println("Teacher already exists");
       }
        return response;
    }

    public  List<TeacherList> listOfTeacher() {

        List<TeacherList> listTeacher=new ArrayList<>();
        teacherRepo.findAll().forEach(i->{
            TeacherList tl=new TeacherList();
            List<ClassList> classLists=new ArrayList<>();
            classNameRepo.findAllByClassValue(i.getClassname().getClassValue()).forEach(
                   className -> {
                       ClassList cl=new ClassList();
                       cl.setClassValue(className.getClassValue());
                       cl.setStrength(className.getStrength());
                       classLists.add(cl);
                      }
            );
            tl.setClassList(classLists);
            tl.setId(i.getId());
            tl.setGender(i.getGender());
            tl.setName(i.getName());
            tl.setSubject(i.getSubject());
            listTeacher.add(tl);
        });

        return listTeacher;
    }

    public  List<TeacherList> findByTeacherName(String name) {

        List<TeacherList> listTeacher=new ArrayList<>();

        teacherRepo.findAllByName(name).forEach(i->{

            TeacherList tl=new TeacherList();
            tl.setId(i.getId());
            tl.setGender(i.getGender());
            tl.setName(i.getName());
            tl.setSubject(i.getSubject());

            List<ClassList> classLists=new ArrayList<>();
            classNameRepo.findAllByClassValue(i.getClassname().getClassValue()).forEach(
                    className -> {
                        ClassList cl=new ClassList();
                        cl.setClassValue(className.getClassValue());
                        cl.setStrength(className.getStrength());
                        classLists.add(cl);
                    }
            );
            tl.setClassList(classLists);
            tl.setId(i.getId());
            tl.setGender(i.getGender());
            tl.setName(i.getName());
            tl.setSubject(i.getSubject());
            listTeacher.add(tl);
        });

        return listTeacher;
    }
}

