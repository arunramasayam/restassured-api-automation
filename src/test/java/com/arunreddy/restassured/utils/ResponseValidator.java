package com.arunreddy.restassured.utils;

import io.restassured.response.Response;

import static io.restassured.internal.matcher.xml.XmlXsdMatcher.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import io.qameta.allure.Step;

public class ResponseValidator {
	
	@Step("Verify Response Status code is {expectedStatusCode}")
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }
	
	@Step("Verify Response JSON Schema")
    public static void validateJsonSchema(Response response,  String schemaPath) {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
	
	@Step("Verify Response XML Schema")
	public static void validateXMLSchema(Response response, String schemaPath) {
		response.then()
                 .assertThat()
                 .body(matchesXsdInClasspath(schemaPath));
	}
}
