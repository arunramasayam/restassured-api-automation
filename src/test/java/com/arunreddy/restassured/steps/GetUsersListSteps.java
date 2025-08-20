package com.arunreddy.restassured.steps;

import static com.arunreddy.restassured.utils.UserUtils.getUsers;
import static org.testng.Assert.assertTrue;

import java.util.List;

import com.arunreddy.restassured.pojo.User;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

public class GetUsersListSteps {
	
	@Step("Send a request to GET all users list")
	public Response sendReqGetUsers() {
		return getUsers();
	}
	
	
	@Step("Verify Users list is not empty")
	public void validateUsersListSize(Response resp) {
		List<User> users=resp.then().
		          extract().as(new TypeRef<List<User>>() {
		          });
		assertTrue(users.size()>0, "User list is empty");
	}

}
