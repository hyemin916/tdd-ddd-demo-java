package au.chrissimon.universityapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentTests {

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void givenIAmAStudent_WhenIRegister() {
        final WebTestClient.ResponseSpec response = bindToServer()
                .baseUrl(baseUrl())
                .build()
                .post()
                .uri("/students")
                .exchange();

        itShouldRegisterANewStudent(response);
        final StudentResponse newStudent = itShouldAllocateANewId(response);
        itShouldShowWhereToLocateNewStudent(response, newStudent);
    }

    private void itShouldRegisterANewStudent(final WebTestClient.ResponseSpec response) {
        response
                .expectStatus()
                .isCreated();
    }

    private StudentResponse itShouldAllocateANewId(final WebTestClient.ResponseSpec response) {
        return response
                .expectBody(StudentResponse.class)
                .value(student -> {
                    assertThat(student.id()).isNotEqualTo(new UUID(0, 0));
                    assertThat(student.id()).isNotNull();
                })
                .returnResult()
                .getResponseBody();
    }

    private void itShouldShowWhereToLocateNewStudent(final WebTestClient.ResponseSpec response, final StudentResponse newStudent) {
        response
                .expectHeader()
                .location(baseUrl() + "/students/" + newStudent.id());
    }
}
