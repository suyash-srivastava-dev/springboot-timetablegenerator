package com.suyash.timetable.ClassName;

import com.suyash.timetable.Response.ResponseClassById;
import com.suyash.timetable.Response.ResponseStd;
import com.suyash.timetable.Teacher.Teacher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClassNameController {
    @Autowired
    private ClassNameService classNameService;

    @Operation(summary = "Add new Class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added Class to Database",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }),
            @ApiResponse(responseCode = "400", description = "Class not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/class",method = RequestMethod.POST)
    public ResponseEntity addEmployee(@RequestBody ClassName classNameCreate )
    {
        Map<String,String > response= classNameService.createClassName(classNameCreate);
        return new ResponseEntity(response,  HttpStatus.resolve(Integer.parseInt(response.get("status"))));
    }

    @Operation(summary = "Get all Class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Class",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassList.class)) }),
            @ApiResponse(responseCode = "400", description = "Class not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/class",method = RequestMethod.GET)
    public ResponseEntity listOfTeachers() {
        List<ClassList> response = classNameService.listOfClassName();
        if (!response.isEmpty())
            return new ResponseEntity(response, HttpStatus.OK);
        else {
            ResponseStd responseStd=new ResponseStd();
            responseStd.setStatus("400");
            responseStd.setDetails("no class exists.");
            return new ResponseEntity(responseStd, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove a Class with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class removed",
                    content = { @Content(mediaType = "text/csv",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Class not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/generate",method = RequestMethod.GET)
    public ResponseEntity csvFile() {
        String response= classNameService.csvTimeTable();
//        String objectsCommaSeparated = response.stream().collect(Collectors.joining(","));
        return new ResponseEntity(response,  HttpStatus.OK);
    }
    @Operation(summary = "Remove a Class with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class removed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }),
            @ApiResponse(responseCode = "400", description = "Class not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/class/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteClass(@PathVariable("id") String id )
    {
        ResponseStd responseStd=classNameService.deleteClassWithTeachers(id);
        if(Objects.equals(responseStd.getStatus(), "200"))
        {
            return new ResponseEntity(responseStd,  HttpStatus.OK);

        }
        else
        return new ResponseEntity(responseStd,  HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get Class by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassList.class)) }),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStd.class)) }) })
    @RequestMapping(value = "/class/{id}",method = RequestMethod.GET)
    public ResponseEntity getClassById(@PathVariable("id") String id ) {
        ResponseClassById responseStd=classNameService.getClassById(id);
        if(Objects.equals(responseStd.getStatus(), "200"))
        {
            return new ResponseEntity(responseStd.getClassList(),  HttpStatus.OK);

        }
        else
            return new ResponseEntity(responseStd,  HttpStatus.BAD_REQUEST);
    }
}
