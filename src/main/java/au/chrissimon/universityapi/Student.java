package au.chrissimon.universityapi;

import java.util.UUID;

public record Student(UUID id) {
    public static Student register(final UUID uuid) {
        return new Student(uuid);
    }
}
