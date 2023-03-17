package com.StudentService.Student;

import com.StudentService.Clients.RESTApi_validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService _studentService;

    @Autowired
    public StudentController(StudentService studentService, RESTApi_validation restApi_validation) {
        this._studentService = studentService;

    }

    @GetMapping
    public List<Student> getStudents(@RequestHeader HttpHeaders headers){
          return _studentService.getStudents(headers);
    }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable String id,@RequestHeader HttpHeaders headers) {
        try{
        return _studentService.getStudent(id,headers);}
        catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/addStudent")
    public ResponseEntity<String> AddStudent(@RequestBody Student student,@RequestHeader HttpHeaders headers) throws FileAlreadyExistsException {
        try{
         return _studentService.AddStudent(student,headers);}
        catch (FileAlreadyExistsException f){
            throw f;
        }
    }

    @PatchMapping("/editStudent/{id}")
    public ResponseEntity<String> EditStudent(@PathVariable String id,@RequestBody Student info,@RequestHeader HttpHeaders headers){
        try{
        return _studentService.EditStudent(id,info,headers);}
        catch (Exception e){
            throw e;
        }
    }



}
