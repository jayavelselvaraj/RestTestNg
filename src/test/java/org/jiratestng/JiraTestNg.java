 package org.jiratestng;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class JiraTestNg {
	SessionFilter session = new SessionFilter();
public static String id; 
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
				"       \"summary\": \" checkin icon not working\",\r\n" + 
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
		  id=js.get("id");
		System.out.println(createissueResponse);
	}
	
	@Test (priority = 3)
	public void addcomment() {
		
		String commentresponse =given().log().all().pathParam("id", "10034").header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \"this is our 20th comment.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().assertThat().statusCode(201)
		.extract().response().asString();
		System.out.println(commentresponse);
	
		
	}  
	@Test(priority = 5)
	public void getcomments(){
	String comments=	given().log().all().pathParam("id", "10034").header("Content-Type","application/json").filter(session)
		.when().get("rest/api/2/issue/{id}/comment")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(comments);
		
		JsonPath jp = new JsonPath(comments);
		int count = jp.get("comments.size()");
		System.out.println("countvalue:"+count);
		
		//String comment = jp.get("comments");
		//System.out.println("string comments:"+comment);
		for (int i=0;i<count;i++) {
		String ids	=jp.get("comments["+i+"].id");
		String message=	jp.get("comments["+i+"].body");
		System.out.println("msg:"+message);
			System.out.println(ids);
			
		/*if (ids.equals("10034") ) {
			String message1=	jp.get("comments["+i+"].body");
				System.out.println("msg:"+message);
				
			}*/
		}
		
	}
	@Test (priority =4)
	public void Addattachement() {
		given().log().all().pathParam("id", id).header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data ").
		multiPart("file ",new File("./jira.txt")).filter(session)
		.when().post("rest/api/2/issue/{id}/attachments")
		.then().assertThat().statusCode(200);
		
	}
	
}
