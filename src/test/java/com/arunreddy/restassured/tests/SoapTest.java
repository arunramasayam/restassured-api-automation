package com.arunreddy.restassured.tests;

import static com.arunreddy.restassured.utils.TestUtils.loadXMLPayload;
import static io.restassured.RestAssured.baseURI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.arunreddy.restassured.steps.SoapSteps;
import com.arunreddy.restassured.utils.AllureLogger;
import static com.arunreddy.restassured.utils.ResponseValidator.*;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;



@Epic("Basic Calculator")
@Feature("Add Two Number")
public class SoapTest {
	
	private static final Logger logger=LoggerFactory.getLogger(SoapTest.class);
	private String requestXML = null;
	private Response resp;
	private SoapSteps steps;
	
	
	@BeforeClass()
	public void setup() {
		baseURI="http://www.dneonline.com";
		String file="./src/test/resources/soap/addRequest.xml";
		requestXML=loadXMLPayload(file, "UTF-8");
		logger.debug("Request Payload XML: {}", requestXML);
		steps=new SoapSteps();
	}
	
	

	@Story("Add Two Numbers successfully")
	@Test(description="Test to add two numbers via POST /calculator.asmx endpoint")
	@Description("Verify Additon Response and XML Schema")
	public void soapAddTest() {
		logger.info("Starting Test: Add two Numbers Soap Request");
		String schemaPath="schemas/addRequest-schema.xsd";
		AllureLogger.logXml("XML Request Payload", requestXML);
		resp=steps.sendSoapReqAdd2Numbers(requestXML);
		validateStatusCode(resp, 200);
		validateXMLSchema(resp, schemaPath);
		steps.validateRespAdditonResult(resp);
		AllureLogger.logXml("Response", resp.asPrettyString());
	    logger.debug("Response: {}", resp.asPrettyString());
	    logger.info("Finishing Test: Add two Numbers Soap Request");
	}
	
	
	
}
