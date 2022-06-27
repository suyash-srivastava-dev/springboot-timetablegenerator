package com.suyash.timetable.Teacher;

import com.suyash.timetable.Response.ResponseStd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "Add new Teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added Teacher to Database",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Teacher.class)) }),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/teacher",method = RequestMethod.POST)
    public ResponseEntity addTeacher(@RequestBody Teacher teacher )
    {
        Map<String,String > response= teacherService.createTeacher(teacher);
        return new ResponseEntity(response,HttpStatus.resolve(Integer.parseInt(response.get("status"))));
    }

    @Operation(summary = "Get all Teachers")
    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Teachers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }),
             })
    public ResponseEntity listOfTeachers(){

        List<TeacherList> response= teacherService.listOfTeacher();
        if(!response.isEmpty())
        return new ResponseEntity(response,HttpStatus.OK);
        else {
            ResponseStd responseStd=new ResponseStd();
            responseStd.setStatus("400");
            responseStd.setDetails("no teacher exists.");
            return new ResponseEntity(responseStd, HttpStatus.BAD_REQUEST);
        }

    }
    @Operation(summary = "Get all Teachers By Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Teacher with mentioned name",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherList.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseStd.class)) })})
    @RequestMapping(value = "/teacher/{id}",method = RequestMethod.GET)
    public ResponseEntity searchTeacher(@PathVariable("id") String name )
    {
        List<TeacherList> response= teacherService.findByTeacherName(name);
        if(!response.isEmpty())
            return new ResponseEntity(response,HttpStatus.OK);
        else {
            ResponseStd responseStd=new ResponseStd();
            responseStd.setStatus("400");
            responseStd.setDetails("no teacher exists.");
            return new ResponseEntity(responseStd, HttpStatus.BAD_REQUEST);
        }
    }
}
