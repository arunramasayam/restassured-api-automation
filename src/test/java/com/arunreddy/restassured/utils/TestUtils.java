package com.arunreddy.restassured.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(TestUtils.class);
	
	
	/**
	 * Generating the user payload based on type
	 * 
	 * @param userPayloadType the type of payload(e.g: 'create') 
	 * @return the user payload as the JSON 
	 */
	
	public static  JSONObject getUserPayload(String userPayloadType) {
	String timestamp=datetime();
	
	Map<String, Object> createUserPayload= Map.of(
			"name", "Arunreddy", 
			"email", "arunreddy"+timestamp+"@test.com", 
			"gender", "male", 
			"status", "active"
			);
	
	Map<String, Object> updateUserPayload= Map.of(
			"name", "Arunareddy", 
			"email", "arunareddy"+timestamp+"@test.com", 
			"gender", "female", 
			"status", "inactive"
			);
	Map<String, Object> patchUserPayload= Map.of(
			"name", "Arunareddy", 
			"status", "inactive"
			);
	
	JSONObject json = userPayloadType.equals("create") ? (new JSONObject(createUserPayload)) : 
		userPayloadType.equals("update") ? (new JSONObject(updateUserPayload)) : 
			userPayloadType.equals("patch") ? (new JSONObject(patchUserPayload)) : 
				null;
	
	if(json==null) {
		logger.error("Invalid user payload json");
	}
	return json;
	}
	
	
	
	/**
	 * To get the date and time
	 * 
	 * @return the date time in particular format as String
	 */
	public static String datetime() {
		return (new SimpleDateFormat("ddMMyyyyhhmmss")).format(new Date());
		
	}
	
	
	
	
	/**
	 * To get the property access-token for authorization
	 * 
	 * @return the access token as a String
	 */
	public static String getAuthToken() {
		return "Bearer "+getProperty("primary.token");
	}
	
	private static final String CONFIG_PATH="./src/test/resources/config.properties";
	
	


	/**
	 * loads the config.properties file and to fetch specific property.
	 * 
	 * @param key the config.properties file path
	 * @return the property value as a String
	 */
	private static String getProperty(String key) {
		String propertyValue="";
		try {
		Properties properties=new Properties();
		FileInputStream fis=new FileInputStream(CONFIG_PATH);
		properties.load(fis);
		propertyValue=properties.getProperty(key);
		} catch (IOException e) {
			System.err.println("Error loading config file: " + e.getMessage());

		}
		return propertyValue;
		
	}
	
	
	
	
  /**
   * Loads and XML payload file from the given file path 
   * 
   * @param filePath the full path of the XML file
   * @param charset the character encoding (e.g: UTF-8)
   * @return the XML content as a string
   */
	
	public static String loadXMLPayload(String filePath, String charset) {
	    try (FileInputStream fis = new FileInputStream(filePath)) {
	        return IOUtils.toString(fis, charset);
	    } catch (IOException e) {
	        logger.error("Unable to process XML request payload file: {}", filePath, e);
	        return ""; 
	    }
	}
	
	

}


