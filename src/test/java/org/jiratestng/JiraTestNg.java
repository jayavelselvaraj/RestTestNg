 package org.jiratestng;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class JiraTestNg {
	SessionFilter session = new SessionFilter();

	@Test (priority =1)
	public void createsession() {
		
		RestAssured.baseURI = "http://localhost:8080/";
		given().log().all().header("Content-Type","application/json").body("{ \"username\": \"jayavelselvaraj\", \"password\": \"Tester@123\" }").filter(session)
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200);
		
	}
	@Test(priority = 2 )
	public void createissue() {
		String createissueResponse = given().log().all().header("Content-Type","application/json").body("{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"RES\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"search icon not working\",\r\n" + 
				"       \"description\": \"step to reproduce\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}").filter(session)
		.when().post("rest/api/2/issue")
		.then().assertThat().statusCode(201)
		.extract().response().asString();
		System.out.println(createissueResponse);
		JsonPath js = new JsonPath(createissueResponse);
		 String id=js.get("id");
		System.out.println(createissueResponse);
	}
	
	@Test (priority = 3)
	public void addcomment() {
		
		given().log().all().patharam("id,id")header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \"this is our first comment.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().assertThat().statusCode(201)
		.extract().response().asString();
		
	}
	@Test
	public void Addattachement() {
		given().log().all().pathParam("id", 111).header("X-Atlassian-Token","no-check").header("Content-Type","multipart/from-data")
		.multiPart("./file ,new file(jira.txt").filter(session)
		.when().post("rest/api/3/issue/{issueIdOrKey}/attachments")
		.then().as
	}
	
}
