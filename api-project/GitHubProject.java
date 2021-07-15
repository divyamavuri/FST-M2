package project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class GitHubProject {
	
	//declare the RequestSpecification object
	RequestSpecification requestSpec;
	
	String sshKey;
	int SSHid;
	
  @BeforeClass
  public void setUp() {
	  requestSpec = new RequestSpecBuilder()
			  .setContentType(ContentType.JSON)
			  .addHeader("Authorization", "token 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")
			  .setBaseUri("https://api.github.com")
			  .build();
	  sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDLqCO2VAhGUIq8wYgxe4dvHVlSDYLO7Mtulh+HlUQanztbm1/VwBfnkXcVxoCQmsAXkTOjm7EpBdPfFMUknIK1mUq+3Xj4CkK6rWX+QPM1A54cuM5yFbedc+JAmI4MKf1DKsuFyOzavhj5vRtqZnfNICcE5xbtfPmMyJLbsQMRi4cj0v0ys2VSUHbVdHIr5IkyUIRk1DRNzcjnqXr6V22Eo++v7k041hW9QpgsKOyriroL6UIfaZ5qVNrAbSj9R7+rHjUyTD8xh2t2D7UOjzVdDbXVfzJ9HwABzoWAMLRkeE8I7JTGCVpA7ED4ESClshxz4L0vDtJIg3hTk6eO";
  }
  
  @Test(priority = 1)
  public void AddKey() {
	  String reqBody = "{\"title\": \"TestAPIKey\",\"key\": "+sshKey+"}";
	
	  Response response = given().spec(requestSpec).when().body(reqBody).post("/user/keys");
	  System.out.println(response.getBody().asPrettyString());
	  
	  SSHid = response.then().extract().path("id");
	  
	  response.then().statusCode(201);
  }
  
  @Test(priority=2)
  public void getSSHkeysAttached() {
	  Response response = given().spec(requestSpec)
				.when().get("/user/keys");
	 String responseBody = response.getBody().asPrettyString();
	 System.out.println(responseBody);
  }
  
  @Test(priority =3)
  public void deleteKeys() {
  	Response response = given().spec(requestSpec) //Use Request Spec
  			.pathParam("keyId", 54447148).when().delete("/user/keys/{keyId}");
  	
  	String resBody = response.getBody().asPrettyString();
  	System.out.println(resBody);
  	
  	//Assertions
  	response.then().statusCode(204);
  	
  }
}
