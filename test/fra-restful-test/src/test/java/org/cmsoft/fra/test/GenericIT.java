package org.cmsoft.fra.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

/**
 * Provides a generic functions and methods used for most commons integration
 * test.
 * 
 * @author Carlos Mario
 *
 */
public abstract class GenericIT {

	@BeforeClass
	public static void setup() {
		String port = System.getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(8080);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "http://localhost/api";
		}

		RestAssured.baseURI = baseHost;

		RestAssured.config = RestAssuredConfig.config()
				.httpClient(HttpClientConfig.httpClientConfig().setParam("http.connection.timeout", 5000));
	}
	
	@AfterClass
	public abstract void afterTest();
}
