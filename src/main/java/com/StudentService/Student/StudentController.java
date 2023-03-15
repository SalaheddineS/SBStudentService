package com.StudentService.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService _studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this._studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
          return _studentService.getStudents();
    }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable String id) {
        try{
        return _studentService.getStudent(id);}
        catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/addStudent")
    public ResponseEntity<String> AddStudent(@RequestBody Student student) throws FileAlreadyExistsException {
        try{
         return _studentService.AddStudent(student);}
        catch (FileAlreadyExistsException f){
            throw f;
        }
    }

    @PatchMapping("/editStudent/{id}")
    public ResponseEntity<String> EditStudent(@PathVariable String id,@RequestBody Student info){
        try{
        return _studentService.EditStudent(id,info);}
        catch (Exception e){
            throw e;
        }
    }

}
