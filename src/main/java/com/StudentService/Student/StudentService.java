package com.StudentService.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Student> getStudents() {
        try {
            return mongoTemplate.findAll(Student.class, "students");
        } catch (Exception e) {
            throw new RuntimeException("Error while Trying to get all the students , for more details check :" + e);
        }
    }

    public Student getStudent(String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            return mongoTemplate.findOne(query, Student.class, "students");
        } catch (Exception e) {
            throw new RuntimeException("Error while getting the student with the id" + id + " for more details check:" + e);
        }
    }

    public ResponseEntity<String> AddStudent(Student student) throws FileAlreadyExistsException {
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


    public ResponseEntity<String> EditStudent(String id, Student info) {
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


}
