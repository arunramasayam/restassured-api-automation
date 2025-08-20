package com.arunreddy.restassured.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

public class SoapSteps {
	@Step("Send soap request to add two numbers")
	public Response sendSoapReqAdd2Numbers(String requestXML) {
		return
				given().
				  filter(new AllureRestAssured()).
				  log().ifValidationFails().
	              contentType("text/xml").
	              accept("text/xml").
	              body(requestXML).
	            when().
	              post("/calculator.asmx");
	}
	
	@Step("Verify Response addition result")
	public void validateRespAdditonResult(Response resp) {
		resp.
        then().
          body("//*:AddResult.text()", equalTo("7"));
	}

}
