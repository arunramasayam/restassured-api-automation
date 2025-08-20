package com.arunreddy.restassured.tests;

import static com.arunreddy.restassured.utils.TestUtils.getAuthToken;
import static com.arunreddy.restassured.utils.TestUtils.getUserPayload;
import static com.arunreddy.restassured.utils.UserUtils.createUser;
import static com.arunreddy.restassured.utils.UserUtils.deleteUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.arunreddy.restassured.pojo.User;
import com.arunreddy.restassured.steps.GetUsersListSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("User Management")
@Feature("Get All Users List")
public class GetUsersTest extends BaseTest {
	
	private static final Logger logger=LoggerFactory.getLogger(GetUsersTest.class);
	private String primaryToken;
	private int userId;
	private Response resp;
	private GetUsersListSteps steps;
	
	@BeforeClass
	public void setupTest(){
		primaryToken=getAuthToken();
		logger.debug("access-token: {}", primaryToken);
		User userCreated=(createUser(getUserPayload("create"), primaryToken)).as(User.class);
		logger.debug("Created User: {}", userCreated);
		steps=new GetUsersListSteps();
	}
	
	
	
	@Story("Get all users list succesfully")
	@Test(description="Test to get all users list via GET /users endpoint")
	@Description("Verify all user list and response JSON schema")
	public void getUsersTest(){
		logger.info("Starting Test: Get All Users List");
		
		String schemaPath="schemas/userslist-schema.json";
		resp=steps.sendReqGetUsers();
		
		validateStatusCode(resp, 200);
		validateJsonSchema(resp, schemaPath);
		steps.validateUsersListSize(resp);
		
		AllureLogger.logJson("Response", resp.asPrettyString());
		logger.debug("Response: {}", resp.asPrettyString());
		logger.info("Finishing Test: Get All Users List");
	}
	
	@AfterClass
	public void cleanUp() {
		if(resp!=null && resp.getStatusCode()==200) {
		deleteUser(userId, primaryToken);
		logger.info("Clean Up: User deleted");
		}
		else {
			logger.warn("Clean Up Skipped: Get All Users List failed");
		}
	}

}
