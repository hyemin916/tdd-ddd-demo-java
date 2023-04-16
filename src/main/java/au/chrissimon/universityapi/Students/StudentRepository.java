package au.chrissimon.universityapi.Students;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface StudentRepository extends JpaRepository<Student, UUID> {
    
}