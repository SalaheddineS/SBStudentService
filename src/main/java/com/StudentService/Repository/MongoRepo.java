package com.StudentService.Repository;

import com.StudentService.Student.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<Student, String> {
    boolean existsByEmail(String email);
}
