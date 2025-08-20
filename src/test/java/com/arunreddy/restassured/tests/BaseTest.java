package com.arunreddy.restassured.tests;

import org.testng.annotations.BeforeClass;

import static  io.restassured.RestAssured.*;

public class BaseTest {
	@BeforeClass
	public void setup() {
		baseURI = "https://gorest.co.in";
	}

}
