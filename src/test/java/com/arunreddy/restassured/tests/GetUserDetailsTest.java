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
import com.arunreddy.restassured.steps.GetUserDetailsSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("User Management")
@Feature("Get a User Details")
public class GetUserDetailsTest extends BaseTest {
	
	private static final Logger logger=LoggerFactory.getLogger(GetUserDetailsTest.class);
	private User user;
	private String primaryToken;
	private Response getUserIDResp;
	private GetUserDetailsSteps getUserDetailsSteps;
	
	@BeforeClass
	public void setupTest() {
		primaryToken=getAuthToken();
		logger.debug("access-token: {}", primaryToken);
	    Response resp=createUser(getUserPayload("create"), primaryToken);
	    user=resp.as(User.class);
	    logger.debug("Created User: {}", user);
	    getUserDetailsSteps=new GetUserDetailsSteps();
	}
	
	@Story("Get User details successfully")
	@Test(description = "Test to get User details via GET /users/user_id endpoint")
	@Description("Verify User Details and Response Schema")
	public void getUserDetailsTest() {
		
		logger.info("Starting Test: Get User Details by ID");
		String schemaPath="schemas/userdetails-schema.json";
		getUserIDResp=getUserDetailsSteps.sendGetUserDetailsReq(primaryToken, user);
		
		validateStatusCode(getUserIDResp, 200);
	    validateJsonSchema(getUserIDResp, schemaPath);
		getUserDetailsSteps.validateUserDetails(user, getUserIDResp);
		
		logger.debug("Response: {}", getUserIDResp.asPrettyString());
		AllureLogger.logJson("Response", getUserIDResp.asPrettyString());
		logger.info("Finishing Test: Get User Details by ID");
	}
	
	@AfterClass
	public void cleanUp() {
		if(getUserIDResp!=null && getUserIDResp.getStatusCode()==200) {
		deleteUser(user.getId(), primaryToken);
		logger.info("Clean Up: Deleting User");
		}
		else {
			logger.warn("Clean Up Skipped: Get User Details By ID is unsuccessful");
		}
	}
}
