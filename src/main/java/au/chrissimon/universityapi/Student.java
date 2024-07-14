package au.chrissimon.universityapi;

import java.util.UUID;

public record Student(UUID id, String name) {
    public static Student register(final String name) {
        return new Student(UUID.randomUUID(), name);
    }
}
