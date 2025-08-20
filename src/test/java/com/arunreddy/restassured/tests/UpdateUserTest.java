package com.arunreddy.restassured.tests;

import static com.arunreddy.restassured.utils.TestUtils.getAuthToken;
import static com.arunreddy.restassured.utils.TestUtils.getUserPayload;
import static com.arunreddy.restassured.utils.UserUtils.createUser;
import static com.arunreddy.restassured.utils.UserUtils.deleteUser;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.arunreddy.restassured.pojo.User;
import com.arunreddy.restassured.steps.UpdateUserSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("User Management")
@Feature("Update User details")
public class UpdateUserTest extends BaseTest{
	
	private static final Logger logger=LoggerFactory.getLogger(UpdateUserTest.class);
	private User user;
	private String primaryToken;
	private JSONObject reqJson;
	private Response resp;
	private UpdateUserSteps steps;
	
	@BeforeClass
	public void testSetup() {
		primaryToken=getAuthToken();
		logger.debug("access-token: {}", primaryToken);
		Response resp=createUser(getUserPayload("create"), primaryToken);
		user=resp.as(User.class);
		logger.debug("User created: {}", user);
		reqJson=getUserPayload("update");
		logger.debug("Request Payload JSON: {}", reqJson);
		steps=new UpdateUserSteps();
	}
	
	@Story("Update User details successfully")
	@Test(description="Test to update user details via PUT /users/user_id endpoint")
	@Description("Verify Updated User Details and JSON schema")
	public void updateUserTest() {
		logger.info("Starting Test: Updating User Details");
		String schemaPath="schemas/userdetails-schema.json";
		AllureLogger.logJson("Request Payload", reqJson.toJSONString());
		resp=steps.sendUserPutReq(primaryToken, reqJson, user);
		
		validateStatusCode(resp, 200);
		validateJsonSchema(resp, schemaPath);
		steps.validateResponseBody(resp, reqJson, user);
		
		
		AllureLogger.logJson("Response", resp.asPrettyString());
		logger.debug("Response: {}", resp);
		logger.info("Finishing Test: Updating User Details");
	}
	

	@AfterClass
	public void cleanUp() {
		if(resp!=null && resp.getStatusCode()==200) {
		deleteUser(user.getId(), primaryToken);
		logger.info("Clean Up: User deleted");
		}
		else {
			logger.warn("Clean Up Skipped: Update User Details failed");
		}
	}

}
