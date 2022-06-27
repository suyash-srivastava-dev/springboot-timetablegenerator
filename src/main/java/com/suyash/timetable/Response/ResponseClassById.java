package com.suyash.timetable.Response;

import com.suyash.timetable.ClassName.ClassList;
import com.suyash.timetable.ClassName.ClassName;
import lombok.Data;

@Data
public class ResponseClassById {
    public String details;
    public String status;
    public ClassList classList;
}
