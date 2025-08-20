package com.arunreddy.restassured.tests;

import static com.arunreddy.restassured.utils.TestUtils.getAuthToken;
import static com.arunreddy.restassured.utils.TestUtils.getUserPayload;
import static com.arunreddy.restassured.utils.UserUtils.createUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.arunreddy.restassured.pojo.User;
import com.arunreddy.restassured.steps.DeleteUserSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("User Management")
@Feature("Delete User")
public class DeleteUserTest extends BaseTest{
	
	private final static Logger logger=LoggerFactory.getLogger(DeleteUserTest.class);
	private int userId;
	private String primaryToken;
	private DeleteUserSteps deleteUserSteps;
	
	@BeforeClass
	public void testSetup() {
		primaryToken=getAuthToken();
		logger.debug("access-token: {}", primaryToken);
		Response resp=createUser(getUserPayload("create"), primaryToken);
		User createdUser=resp.as(User.class);
		logger.debug("User Created: {}", createdUser);
		userId=createdUser.getId();
		deleteUserSteps=new DeleteUserSteps();
	}
	

	
	@Story("Delete a user successfully")
	@Test(description = "Test to delete a user via DELETE /users/user_id endpoint")
	@Description("Verify User Deletion")
	public void deleteUserTest() {
		logger.info("Starting Test: Deleting User");
		Response resp=deleteUserSteps.sendReqDeleteUser(userId, primaryToken);
		AllureLogger.logText("Response", resp.asPrettyString());
		validateStatusCode(resp, 204);
		logger.debug("Response: {}", resp.asPrettyString());
		logger.info("Finishing Test: Deleting User");
	}
}
