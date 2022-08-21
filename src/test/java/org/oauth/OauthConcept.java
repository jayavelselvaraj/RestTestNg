package org.oauth;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class OauthConcept {

	public static void main(String[] args) {
		
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qihAGMI_dSLl7kefvc6Q67Ra4efNqrlErgd4IzyFJOsrmUP5p3WL1Ec12-uphBdrQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		
		String[] split1 = url.split("code=");
		String[] split2 = split1[1].split("&scope=");
		
		
		String code = split2[0];
		// access token
		String accesstoken = given().log().all().queryParams("code",code)
		.queryParams("scope","https://www.googleapis.com/auth/userinfo.email")
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code").header("Content-Type","application/json").urlEncodingEnabled(false)
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath jp = new JsonPath(accesstoken);
		String token = jp.get("access_token");
		
		System.out.println("Stringtoken:"+token);
		
		//get course details
	String getcourse =	given().log().all().queryParam("access_token", token).header("Content-Type","application/json").urlEncodingEnabled(false)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		
		.asString();
	System.out.println(getcourse);
		
	}
}
