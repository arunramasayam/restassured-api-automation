package com.arunreddy.restassured.utils;

import io.qameta.allure.Attachment;

public class AllureLogger {

	@Attachment(value="{0}", type="application/json")
	public static String logJson(String title, String json) {
		return json;
	}
	
	@Attachment(value="{0}", type="text/plain")
	public static String logText(String title, String message) {
		return message;
	}
	
	@Attachment(value="{0}", type="text/xml")
	public static String logXml(String title, String xml) {
		return xml;
	}
}
