package package1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity2 {

	// Set base URL
	final static String ROOT_URI = "https://petstore.swagger.io/v2/user";


	@Test(priority=1)
	public void addNewUserFromFile() throws IOException {
		// Import JSON file
        FileInputStream inputJSON = new FileInputStream("src/package1/post_request.json");
        // Read JSON file as String
        String reqBody = new String(inputJSON.readAllBytes());
 

		Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(ROOT_URI);

		inputJSON.close();
		// Assertion
		response.then().body("code", equalTo(200));
		response.then().body("message", equalTo("divyaM"));
	}

	@Test(priority=2)
	public void getUserInfo() {
		
		// Import JSON file to write to
        File outputJSON = new File("src/package1/userGETResponse.json");
        
		Response response = given().contentType(ContentType.JSON)
				.when().pathParam("username", "divyaM")
				.get(ROOT_URI+"/{username}");
		
		 // Get response body
        String resBody = response.getBody().asPrettyString();
 
        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

		// Assertion
		response.then().body("id", equalTo(12321));
		response.then().body("username", equalTo("divyaM"));
        response.then().body("firstName", equalTo("divya"));
        response.then().body("lastName", equalTo("mavuri"));
        response.then().body("email", equalTo("divyam@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9876543210"));
	}
	
	
	@Test(priority=3)
	public void delete_request() {
		Response response = 
				given().contentType(ContentType.JSON) // Set headers
				.when().pathParam("username", "divyaM") // Set path parameter
				.delete(ROOT_URI + "/{username}"); // Send DELETE request

		// Assertion
		response.then().body("code", equalTo(200));
		response.then().body("message", equalTo("divyaM"));

	}
}
