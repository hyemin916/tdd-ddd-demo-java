package au.chrissimon.universityapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StudentController {

    @PostMapping("/students")
    ResponseEntity<Student> registerNewStudent() {
        final Student newStudent = Student.register(UUID.randomUUID());
        return ResponseEntity.created(null).body(newStudent);
    }
}
