package com.cmsoft.fr.module.person.controller;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmsoft.fr.module.base.controller.Response;
import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.person.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController {

	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;

	@PostMapping
	public ResponseEntity<Response<PersonDto>> save(@RequestBody PersonDto personDto) {

		Response<PersonDto> response = new Response<>();
		LOG.info("It has received new request for creating new user");
		

		try {
			PersonDto savedDto = personService.save(personDto);
			response.setHttpStatusCode(HttpServletResponse.SC_CREATED);
			response.setMessage("The person was saved successfull");
			response.setObject(savedDto);
		} catch (IllegalArgumentException e) {
			response.setMessage(
					"Some mandatories person attributes are missing. Check api documetation for POST:/person");
			response.setHttpStatusCode(HttpServletResponse.SC_OK);
//		} catch (EntityExistsException e) {
//			response.setMessage("The username is already registered. Try with other one");
//			response.setHttpStatusCode(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			response.setMessage("An error has occurred, we are working to solve it.");
			response.setHttpStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@Autowired
	public void setPersonSrvice(PersonService personService) {
		this.personService = personService;
	}

}
