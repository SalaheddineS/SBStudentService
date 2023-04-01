package com.StudentService.Student;

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

    @PostMapping("/verify")
    public boolean existsBySkill(@RequestBody Map<String, String> skill) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/api/v1/association/VerifySkill";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a map to hold the skill value
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("skill", skill.get("skill"));

        // Pass the map directly to the HttpEntity constructor
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Use exchange instead of postForObject for better error handling
        boolean response = restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class).getBody();
        return response;
    }


}
