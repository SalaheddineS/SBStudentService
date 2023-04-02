package com.StudentService.Student;

import com.StudentService.Clients.AssociationAPI;
import com.StudentService.Clients.RESTApi_validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    AssociationAPI _associationAPI;
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

    @PostMapping("/addSkill")
    public ResponseEntity<String> addskill(@RequestBody Map<String,String> info){
        return _studentService.addSkillToUser(info.get("email"),info.get("skill"));
    }


}
