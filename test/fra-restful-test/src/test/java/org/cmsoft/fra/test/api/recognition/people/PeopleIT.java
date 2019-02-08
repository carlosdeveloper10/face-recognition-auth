package org.cmsoft.fra.test.api.recognition.people;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.cmsoft.fra.test.GenericIT;
import org.cmsoft.fra.test.UtilTest;
import org.junit.Test;

/**
 * It  for recognition/people.
 */
public class PeopleIT extends GenericIT {

	@Test
	public void step1_whenPersonDoesNoExistThenApiReturn201AndFaceFeaturesNotFoud_1() {
		given()
			.body(UtilTest.getFilesFromResource("json/people/not-exist-person(messi-face).json"))
			.contentType("application/json")
		.when()
		.log().all()
			.post("/recognition/people")
		.then()
			.assertThat().statusCode(is(201))
			.and()
			.body("message", is("FACIAL_FEATURES_NOT_FOUND"));
	}
	
	@Test
	public void step2_whenPersonExistsThenApiReturn201AndFaceFeaturesFoud_1() {
		given()
			.body(UtilTest.getFilesFromResource("json/people/not-exist-person(CR7-face).json"))
			.contentType("application/json")
		.when()
		.log().all()
			.post("/recognition/people")
		.then()
			.assertThat().statusCode(is(201))
			.and()
			.body("message", is("FACIAL_FEATURES_FOUND"));
	}
}
