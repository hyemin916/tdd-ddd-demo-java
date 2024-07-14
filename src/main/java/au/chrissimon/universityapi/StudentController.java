package au.chrissimon.universityapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class StudentController {

    @PostMapping("/students")
    ResponseEntity<Student> registerNewStudent() {
        final Student newStudent = Student.register(UUID.randomUUID());
        final URI newStudentLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.id())
                .toUri();
        return ResponseEntity.created(newStudentLocation).body(newStudent);
    }
}
