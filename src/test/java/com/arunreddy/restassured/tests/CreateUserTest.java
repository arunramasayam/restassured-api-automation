package com.arunreddy.restassured.tests;

import static com.arunreddy.restassured.utils.TestUtils.getAuthToken;
import static com.arunreddy.restassured.utils.TestUtils.getUserPayload;
import static com.arunreddy.restassured.utils.UserUtils.deleteUser;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.arunreddy.restassured.steps.CreateUserSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;


@Epic("User Management")
@Feature("Create New User API")
public class CreateUserTest extends BaseTest {
	
	private static final Logger logger=LoggerFactory.getLogger(CreateUserTest.class);
	private JSONObject requestJson;
	private String primaryToken;
	private Response resp;
	private CreateUserSteps userSteps;
	
	
	@BeforeClass
	public void testSetup() {
		requestJson=getUserPayload("create");
		logger.debug("Request Payload JSON: {}", requestJson);
		primaryToken=getAuthToken();
		logger.debug("access-token: {}", primaryToken.toString());
		userSteps=new CreateUserSteps();
	}
	
	

	
	@Story("Create a new user successfully")
	@Test(description = "Test to create a new user via POST /users endpoint ")
	@Description("Verify user creation and response schema")
	public void creatingUserTest() {
		logger.info("Starting Test: Creating New User");
		AllureLogger.logJson("Request Payload", requestJson.toJSONString());
		String schemaPath="schemas/createuser-schema.json";
		
	    resp=userSteps.sendCreateUserReq(requestJson, primaryToken);
	    AllureLogger.logJson("Response", resp.asPrettyString());
	    
	    validateStatusCode(resp, 201);
	    validateJsonSchema(resp, schemaPath);
	    userSteps.validateUserReponseData(resp, requestJson);
	
	    
	    logger.debug("Response: {}", resp.asPrettyString());
	    logger.info("Finishing Test: Creating New User");
		
	}
	

	
	@AfterClass
	public void cleanUp() {
		if(resp!=null && resp.getStatusCode()==201) {
		int userId=resp.jsonPath().getInt("id");
		deleteUser(userId, primaryToken);
		logger.info("Clean Up: User deleted");
		}
		else {
			logger.warn("CleanUp skipped: User creation not successful");
		}
	}

}
