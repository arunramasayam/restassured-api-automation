package com.arunreddy.restassured.steps;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import com.arunreddy.restassured.pojo.User;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserDetailsSteps {
	@Step("Send GET request a user details by ID")
	public Response sendGetUserDetailsReq(String primaryToken, User user) {
		return
				given().
				   filter(new AllureRestAssured()).
				   log().ifValidationFails().
				   accept(ContentType.JSON).
				   header("Authorization", primaryToken).
				when().
				   get("/public/v2/users/"+user.getId());
	}
	
	@Step("Verify User Details")
	public void validateUserDetails(User user, Response resp) {
		User userByID=resp.as(User.class);
		assertTrue(user.equals(userByID), "User details didn't match");
	}
	

}
