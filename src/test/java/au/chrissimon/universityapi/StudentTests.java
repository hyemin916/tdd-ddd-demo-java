package au.chrissimon.universityapi;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentTests {

	@Value(value="${local.server.port}")
	private int port;

	private static final String STUDENT_PATH = "/students";

	private String baseUri() { return "http://localhost:" + port; }

	private WebTestClient newWebClient() {
		return WebTestClient
			.bindToServer()
				.baseUrl(baseUri())
				.build();
	}

	private ResponseSpec registerStudent(RegisterStudentRequest studentRequest) {
		return newWebClient()
			.post()
				.uri(STUDENT_PATH)
				.bodyValue(studentRequest)
			.exchange();
	}

	@Test
	public void givenIAmAStudent_WhenIRegister() throws Exception {
		RegisterStudentRequest studentRequest = new RegisterStudentRequest("Test Student");

		ResponseSpec response = registerStudent(studentRequest);

		itShouldRegisterANewStudent(response);
		StudentResponse newStudent = itShouldAllocateANewId(response);
		itShouldShowWhereToLocateNewStudent(response, newStudent);
		itShouldConfirmStudentDetails(studentRequest, newStudent);
	}

	private void itShouldRegisterANewStudent(ResponseSpec response) {
		response
			.expectStatus()
			.isCreated();
	}

	private StudentResponse itShouldAllocateANewId(ResponseSpec response) {
		return response
			.expectBody(StudentResponse.class)
				.value(student -> {
					assertThat(student.getId()).isNotEqualTo(new UUID(0, 0));
					assertThat(student.getId()).isNotNull();

				})
				.returnResult()
				.getResponseBody();
	}

	private void itShouldShowWhereToLocateNewStudent(ResponseSpec response, StudentResponse newStudent) {
		response
			.expectHeader()
				.location(baseUri() + STUDENT_PATH + "/" + newStudent.getId());
	}

	private void itShouldConfirmStudentDetails(RegisterStudentRequest studentRequest, StudentResponse newStudent) {
		assertThat(newStudent.getName()).isEqualTo(studentRequest.getName());
	}

	@Test
	public void givenIHaveRegistered_WhenICheckMyDetails()
	{
		RegisterStudentRequest studentRequest = new RegisterStudentRequest("Test Student");

		URI newStudentLocation = registerStudent(studentRequest)
				.expectBody(StudentResponse.class)
				.returnResult()
				.getResponseHeaders().getLocation();

		ResponseSpec response = newWebClient()
			.get()
				.uri(newStudentLocation)
			.exchange();
			
		itShouldFindTheNewStudent(response);
	}

	private void itShouldFindTheNewStudent(ResponseSpec response) {
		response
			.expectStatus()
			.isOk();
	}
}