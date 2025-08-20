package com.arunreddy.restassured.steps;

import static com.arunreddy.restassured.utils.UserUtils.deleteUser;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DeleteUserSteps {
	@Step("Send request to delete a user")
	public Response sendReqDeleteUser(int userId, String primaryToken) {
		return deleteUser(userId, primaryToken);
	}
	
}
