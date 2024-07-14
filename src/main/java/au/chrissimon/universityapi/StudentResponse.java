package au.chrissimon.universityapi;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String name
) {
}
