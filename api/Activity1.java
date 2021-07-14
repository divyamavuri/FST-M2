package package1;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {

	// Set base URL
	final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";


	@Test(priority=1)
	public void post_request() {
		// Create JSON request
		String reqBody = "{"
				+ "\"id\": 12321,"
				+ "\"name\": \"Riley\","
				+ " \"status\": \"alive\""
				+ "}";

		Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(ROOT_URI);

		// Assertion
		response.then().body("id", equalTo(77232));
		response.then().body("name", equalTo("Riley"));
		response.then().body("status", equalTo("alive"));
	}

	@Test(priority=2)
	public void get_request() {
		Response response = given().contentType(ContentType.JSON)
				.when().pathParam("petId", "12321")
				.get(ROOT_URI+"/{petID}");

		// Assertion
		response.then().body("id", equalTo(12321));
		response.then().body("name", equalTo("Riley"));
		response.then().body("status", equalTo("alive"));

	}
	@Test(priority=3)
	public void delete_request() {
		Response response = 
				given().contentType(ContentType.JSON) // Set headers
				.when().pathParam("petId", "12321") // Set path parameter
				.delete(ROOT_URI + "/{petId}"); // Send DELETE request

		// Assertion
		response.then().body("code", equalTo(200));
		response.then().body("message", equalTo("12321"));

	}
}
