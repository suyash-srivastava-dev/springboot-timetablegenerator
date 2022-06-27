package com.suyash.timetable.ClassName;

import com.suyash.timetable.Response.ResponseClassById;
import com.suyash.timetable.Response.ResponseStd;
import com.suyash.timetable.Teacher.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassNameService {

    @Autowired
    private ClassNameRepo classNameRepo;
    @Autowired
    private TeacherRepo teacherRepo;


    public Map<String,String> createClassName (ClassName classNametoAdd)
    {
        Map<String,String > response=new HashMap<>();

        if(classNameRepo.existsByClassValue(classNametoAdd.getClassValue()))
        {
            response.put("status","404");
            response.put("details","class with the same name already exists.");
        }
        else
        {
            classNameRepo.save(classNametoAdd);
            response.put("status","201");
            response.put("details","class created");
        }
        return response;
    }

    public List<ClassList> listOfClassName() {
        List<ClassList> listClass=new ArrayList<>();
        classNameRepo.findAll().forEach(i->{
            ClassList tl=new ClassList();
            tl.setClassValue(i.getClassValue());
            tl.setStrength(i.getStrength());
            listClass.add(tl);
        });
        return listClass;
    }

    public String  csvTimeTable()
    {
        List<List<String>> csvValue=new ArrayList<>();
        classNameRepo.findById("na").ifPresent(
                i->classNameRepo.deleteById("na"));
        classNameRepo.findAll().forEach(eachClass-> {
            List<String> subjectList=new ArrayList<>();
            teacherRepo.findByClassname(eachClass).forEach(eachTeacher->{
                subjectList.add(eachTeacher.getSubject());
            });
            for(int day=0;day<5;day++)
            {
                List<String> daysName=new ArrayList<String>(Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday"));
                ArrayList<String> al= (ArrayList<String>) dailySubjects(subjectList);
//                ArrayList<String> al= (ArrayList<String>) givenList_whenSeriesLengthChosen_shouldReturnRandomSeries(subjectList);
                al.add(0,daysName.get(day));
                al.add(0,eachClass.getClassValue());
                csvValue.add(al);
            }
        });

        String response="Class,Days,Session1,Session2,Session3,Session4,Session5\n";
        int j;
        for (int i = 0; i < csvValue.size(); i++) {

            for ( j = 0; j < csvValue.get(0).size()-1; j++) {
                response=response+csvValue.get(i).get(j)+",";
            }
            response=response+csvValue.get(i).get(j)+"\n";
            }

        ClassName className= new ClassName();
        className.setClassValue("na");
        className.setStrength(0);

        classNameRepo.save(className);
        return response;


    }


    public List<String> dailySubjects(List<String> subjectList)
    {
       int teachersNum=subjectList.size();
        Random random = new Random();
        // generate random number from 0 to subjects
        int number = random.nextInt(teachersNum);
        List<String> todaySubjects=new ArrayList<>();
//        todaySubjects.add(day.get(i));
        for (int i = 0; i < teachersNum; i++) {
            todaySubjects.add(subjectList.get(number%teachersNum));
            number++;
        }
        return todaySubjects;
    }

    public ResponseStd deleteClassWithTeachers(String id)
    {
        ResponseStd responseStd=new ResponseStd();
        ClassName className= new ClassName();
        className.setClassValue("na");
        className.setStrength(0);
        classNameRepo.save(className);
        classNameRepo.findById(id).ifPresentOrElse(
                (item)->{
                    teacherRepo.findAllByClassname(item).forEach(teachers->{
                        teachers.setClassname(className);
                    });
                    classNameRepo.delete(item);
                },
                ()->{
                    responseStd.setDetails("No class with the given ID found");
                    responseStd.setStatus("400");
                });
        responseStd.setDetails("Removed class and the assigned teacher to the empty class");
        responseStd.setStatus("200");
        return responseStd;
    }

    public ResponseClassById getClassById(String id)
    {
        ResponseClassById responseClassById=new ResponseClassById();
        classNameRepo.findById(id).ifPresentOrElse(
                (item)->{
                    ClassList tl=new ClassList();
                    tl.setClassValue(item.getClassValue());
                    tl.setStrength(item.getStrength());
                    responseClassById.setClassList(tl);
                    responseClassById.setDetails("Found Class with ID");
                    responseClassById.setStatus("200");
                },
                ()->{
        responseClassById.setDetails("Found Class with ID");
        responseClassById.setStatus("400");
                }
        );
        return responseClassById;
    }
}
