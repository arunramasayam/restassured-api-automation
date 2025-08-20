package com.arunreddy.restassured.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.json.simple.JSONObject;

import com.arunreddy.restassured.pojo.User;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserSteps {
	@Step("Send request to update user details via PUT /users/user_id endpoint")
	public Response sendUserPutReq(String primaryToken, JSONObject reqJson, User user) {
		return given().
				   filter(new AllureRestAssured()).
				   log().ifValidationFails().
				   contentType(ContentType.JSON).
				   accept(ContentType.JSON).
				   header("Authorization", primaryToken).
				   body(reqJson).
				when().
				   put("/public/v2/users/"+user.getId());
				
	}
	
	@Step("Verify Updated User Response Body")
	public void validateResponseBody(Response resp, JSONObject reqJson, User user) {
		resp.
		then().
		   body("id", equalTo(user.getId())).
		   body("name", equalTo(reqJson.get("name"))).
		   body("email", equalTo(reqJson.get("email"))).
		   body("gender", equalTo(reqJson.get("gender"))).
		   body("status", equalTo(reqJson.get("status")));
	}
	
}
