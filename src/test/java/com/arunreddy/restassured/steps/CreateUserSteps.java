package com.arunreddy.restassured.steps;

import static com.arunreddy.restassured.utils.UserUtils.createUser;
import static org.hamcrest.CoreMatchers.equalTo;

import org.json.simple.JSONObject;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CreateUserSteps {

	@Step("Send create new user request")
	public Response  sendCreateUserReq(JSONObject requestJson, String primaryToken) {
		return createUser(requestJson, primaryToken);
	}
	
	@Step("Verify User Response data")
	public void validateUserReponseData(Response resp, JSONObject requestJson) {
		resp.then().
		  body("name",equalTo(requestJson.get("name"))).
	      body("email",equalTo(requestJson.get("email"))).
	      body("gender",equalTo(requestJson.get("gender"))).
	      body("status",equalTo(requestJson.get("status")));
	}
	
}
