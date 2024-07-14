package au.chrissimon.universityapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class StudentController {

    @PostMapping("/students")
    ResponseEntity<Student> registerNewStudent(@RequestBody final Student registerStudentRequest) {
        final Student newStudent = Student.register(registerStudentRequest.name());
        final URI newStudentLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.id())
                .toUri();
        return ResponseEntity.created(newStudentLocation).body(newStudent);
    }
}
