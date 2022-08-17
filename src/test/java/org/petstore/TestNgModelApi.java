package org.petstore;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class TestNgModelApi {

@Test(priority=1)
 public void addpet() {
RestAssured.baseURI = "https://petstore.swagger.io/v2";
   String Response = given().log().all().header("Content-Type","application/json").body("{\r\n" + 
   		"    \"id\": 123,\r\n" + 
   		"    \"category\": {\r\n" + 
   		"        \"id\": 12,\r\n" + 
   		"        \"name\": \"cat\"\r\n" + 
   		"    },\r\n" + 
   		"    \"name\": \"momo\",\r\n" + 
   		"    \"photoUrls\": [\r\n" + 
   		"        \"string\"\r\n" + 
   		"    ],\r\n" + 
   		"    \"tags\": [\r\n" + 
   		"        {\r\n" + 
   		"            \"id\": 11,\r\n" + 
   		"            \"name\": \"abroad\"\r\n" + 
   		"        }\r\n" + 
   		"    ],\r\n" + 
   		"    \"status\": \"available\"\r\n" + 
   		"}")
   .when().post("/pet")
   .then().log().all().assertThat().statusCode(200).body("id", equalTo(123))
   .extract().response().asString();
	
	System.out.println("End the programe");
	System.out.println(Response);
	
}
	@Test (priority=3)
	public void getid() {
     String getResponse = given().log().all().pathParam("id", 123).header("Content-Type","application/json")
     .when().get("/pet/{id}")
     .then().log().all().assertThat().statusCode(200)
     .extract().response().asString();
		JsonPath js = new JsonPath(getResponse);
		
		//int  id = js.get("id");
	//	System.out.println(id);
		
		int  id1 = js.get("category.id");
		System.out.println(id1);
		
		//int id2 = js.get("tags[0].id");
		//System.out.println(id2);
	}
@Test (priority=2)
public void update() {
	String getResponse1 = given().log().all().header("Content-Type","application/json").body("{\r\n" + 
			"    \"id\": 123,\r\n" + 
			"    \"category\": {\r\n" + 
			"        \"id\": 12,\r\n" + 
			"        \"name\": \"cat\"\r\n" + 
			"    },\r\n" + 
			"    \"name\": \"momo\",\r\n" + 
			"    \"photoUrls\": [\r\n" + 
			"        \"string\"\r\n" + 
			"    ],\r\n" + 
			"    \"tags\": [\r\n" + 
			"        {\r\n" + 
			"            \"id\": 11,\r\n" + 
			"            \"name\": \"abroad\"\r\n" + 
			"        }\r\n" + 
			"    ],\r\n" + 
			"    \"status\": \"sold\"\r\n" + 
			"}")
	.when().put("/pet")
	.then().log().all().assertThat().statusCode(200)
	.extract().response().asString();
	//JsonPath js = new JsonPath(getResponse1);
//String status = js.get("Status");
	//System.out.println(status);
	
}

}
