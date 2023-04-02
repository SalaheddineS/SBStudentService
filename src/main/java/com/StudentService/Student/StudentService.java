package com.StudentService.Student;

import com.StudentService.Clients.AssociationAPI;
import com.StudentService.Clients.RESTApi_validation;
import com.StudentService.Repository.MongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    @Autowired
    MongoRepo _mongoRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RESTApi_validation _restApi_validation;
    @Autowired
    AssociationAPI _associationAPI;

    public List<Student> getStudents(HttpHeaders headers) {
        boolean isAuthentified = _restApi_validation.ValidateAuthentified(headers.getFirst("Authorization"));
        if(!isAuthentified) throw new RuntimeException("You are not authentified");
        try {
            return mongoTemplate.findAll(Student.class, "students");
        } catch (Exception e) {
            throw new RuntimeException("Error while Trying to get all the students , for more details check :" + e);
        }
    }

    public Student getStudent(String id,HttpHeaders headers) {
        boolean isAuthentified = _restApi_validation.ValidateAuthentified(headers.getFirst("Authorization"));
        if(!isAuthentified) throw new RuntimeException("You are not authentified");
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            return mongoTemplate.findOne(query, Student.class, "students");
        } catch (Exception e) {
            throw new RuntimeException("Error while getting the student with the id" + id + " for more details check:" + e);
        }
    }

    public ResponseEntity<String> AddStudent(Student student, HttpHeaders headers) throws FileAlreadyExistsException {
            boolean isAuthentified = _restApi_validation.ValidateAuthentified(headers.getFirst("Authorization"));
        if(!isAuthentified) throw new RuntimeException("You are not authentified");
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(student.getEmail()));
            boolean emailExists = mongoTemplate.exists(query, Student.class, "students");
            if (emailExists) throw new FileAlreadyExistsException("Existant email");
            mongoTemplate.insert(student, "students");
            return ResponseEntity.status(HttpStatus.CREATED).body("Student " + student.getName() + " added successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error on the Student Creation Process, for more details check this:" + e);
        }
    }


    public ResponseEntity<String> EditStudent(String id, Student info,HttpHeaders headers) {
        boolean isAuthentified = _restApi_validation.ValidateAuthentified(headers.getFirst("Authorization"));
        if(!isAuthentified) throw new RuntimeException("You are not authentified");
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Update update = new Update();
            if (info.getName() != null && !info.getName().isEmpty()) update.set("name", info.getName());
            if (info.getEmail() != null && !info.getEmail().isEmpty()) update.set("email", info.getEmail());
            if (info.getAge() != null && info.getAge() > 0) update.set("age", info.getAge());
            mongoTemplate.updateFirst(query, update, Student.class, "students");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student updated successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error on the editing process, for more details check this:" + e);
        }
    }

    public ResponseEntity<String> addSkillToUser(String email,String skill){
        String url="http://localhost:8082/api/v1/association/addAssociation";
        boolean emailExists=_mongoRepo.existsByEmail(email);
        if(!emailExists) throw new RuntimeException("Email doesn't exist");
        boolean skillExists= _associationAPI.existsBySkill(skill);
        if(!skillExists) throw new RuntimeException("Skill doesn't exists");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        Map<String,String> body=new HashMap<>();
        body.put("email",email);
        body.put("skill",skill);
        HttpEntity<Map<String,String>> entity=new HttpEntity<>(body,headers);
        ResponseEntity<String> result=restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
        return result;
    }

}
