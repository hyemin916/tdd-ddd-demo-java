package au.chrissimon.universityapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.reactive.server.WebTestClient.bindToServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentTests {

    @LocalServerPort
    private int port;

    @Test
    public void givenIAmAStudent_WhenIRegister() {
        final WebTestClient.ResponseSpec response = bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri("/students")
                .exchange();

        itShouldRegisterANewStudent(response);
    }

    private WebTestClient.ResponseSpec itShouldRegisterANewStudent(final WebTestClient.ResponseSpec response) {
        return response
                .expectStatus()
                .isCreated();
    }
}
