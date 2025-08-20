package com.arunreddy.restassured.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.json.simple.JSONObject;

import com.arunreddy.restassured.pojo.User;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchUserSteps {
	@Step("Send request to patch user details")
	public Response sendReqPatchUserDetails(String primaryToken, User user, JSONObject reqJson) {
		return given().
				  filter(new AllureRestAssured()).
				  log().ifValidationFails().
				  contentType(ContentType.JSON).
				  accept(ContentType.JSON).
				  header("Authorization", primaryToken).
				  body(reqJson).
			    when().
			      patch("/public/v2/users/"+user.getId());
				
	}
	
	
	@Step("Verify response body after user patch")
	public void validateRespBody(Response resp, User user, JSONObject reqJson) {
		resp.
	    then().
	      body("id", equalTo(user.getId())).
	      body("name", equalTo(reqJson.get("name"))).
	      body("status", equalTo(reqJson.get("status"))).
	      body("email", equalTo(user.getEmail())).
	      body("gender", equalTo(user.getGender()));
	}
	 
}
