package org.cmsoft.fra.test.api.resource.people;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.cmsoft.fra.test.GenericIT;
import org.cmsoft.fra.test.UtilTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * Class for api/people IT.
 * 
 * @author carlos
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PeopleIT extends GenericIT {

	@Test
	public void step1_whenPersonIsSavedSuccessfullThenApiReturn201_IT() {
		
		given()
			.body(UtilTest.getFilesFromResource("json/people/ok-person.json"))
			.contentType("application/json")
		.when()
			.post("/people")
		.then()
			.assertThat().statusCode(201);
	}
	
	@Test
	public void step2_whenPersonExistsThenApiReturn200_IT() {
		given()
			.body(UtilTest.getFilesFromResource("json/people/ok-person.json"))
			.contentType("application/json")
		.when()
			.post("/people")
		.then()
			.assertThat().statusCode(200)
			.and()
			.body("message", is("The username is already registered. Try with other one"));
	}
	
	@Test
	public void step3_whenPersonRequestIsBadThenApiReturn200() {
		
		given()
			.body(UtilTest.getFilesFromResource("json/people/bad-person.json"))
			.contentType("application/json")
		.when()
			.post("/people")
		.then()
			.assertThat().statusCode(200)
			.and()
			.body("message", is("Some mandatories person attributes are missing. Check api documetation for POST:/person"));
	}
}