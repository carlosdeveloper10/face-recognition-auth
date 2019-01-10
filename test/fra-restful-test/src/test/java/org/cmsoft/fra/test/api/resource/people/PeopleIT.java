package org.cmsoft.fra.test.api.resource.people;

import static io.restassured.RestAssured.given;
import io.restassured.itest.java.support.WithJetty;
import org.cmsoft.fra.test.UtilTest;
import org.junit.Test;


/**
 * Class for api/people IT.
 * 
 * @author carlos
 *
 */
public class PeopleIT extends WithJe{

	@Test
	public void whenPersonIsSavedSuccessfullThenApiReturn201() {
		
		
		
		given()
			.body(UtilTest.getFilesFromResource("json/people/people.json"))
		.when()
			.post("/people")
		.then()
			.assertThat().statusCode(201);
	}
}
