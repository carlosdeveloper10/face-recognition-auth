package org.cmsoft.fra.test;

import org.apache.http.client.methods.RequestBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;

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
			port = "8080";
		}
		RestAssured.port = Integer.valueOf(port);

		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;

//		String basePath = System.getProperty("api.resource");
//		if (basePath == null) {
//			basePath = "/api";
//		}
//		RestAssured.basePath = basePath;

		RestAssured.config = RestAssuredConfig.config()
				.httpClient(HttpClientConfig.httpClientConfig()
			    .setParam("http.connection.timeout", 5000));

	
		//RestAssured.requestSpecification = new RequestBuilder().build().g
	
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.);
	}
}
