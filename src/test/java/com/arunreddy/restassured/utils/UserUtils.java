package com.arunreddy.restassured.utils;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserUtils{
	
	private final static Logger logger=LoggerFactory.getLogger(UserUtils.class);
	
	
	/**
	 * Calls the Get all Users list API
	 * 
	 * @return the response of the API 
	 */
	public static Response getUsers() {
		Response resp = null;
		try {

		
		resp =	given().
				   filter(new AllureRestAssured()).
				   log().ifValidationFails().
				   accept(ContentType.JSON).
				when().
				   get("/public/v2/users");
		}
		catch(Exception e) {
			logger.error("Failed to fetch user list from /public/v2/users. Reason: " + e.getMessage());
		}
		
		return resp;
	}
	
	
	/**
	 * Calls the Create new user API
	 * 
	 * @param requestJson the JSON payload with new user details
	 * @param primaryToken then access-token required for authorization
	 * @return the response of the API call
	 */
	public static Response createUser(JSONObject requestJson, String primaryToken) {
		return 
				given().
				  filter(new AllureRestAssured()).
		          log().ifValidationFails().
		          contentType(ContentType.JSON).
		          accept(ContentType.JSON).
		          body(requestJson).
		          header("Authorization", primaryToken).
	           when().
	              post("/public/v2/users");
	}
	
	
	/**
	 * Calls the API to delete the user
	 * 
	 * @param userId the user ID required for deleting the user
	 * @param primaryToken the access-token required for authorization
	 * @return
	 */
	public static Response deleteUser(int userId, String primaryToken) {
		return 
				given().
				  filter(new AllureRestAssured()).
				  log().ifValidationFails().
				  noContentType().
				  accept(ContentType.JSON).
				  header("Authorization", primaryToken).
				when().
				  delete("/public/v2/users/"+userId);
	}
}
