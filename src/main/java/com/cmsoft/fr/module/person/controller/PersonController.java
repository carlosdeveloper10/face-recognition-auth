package com.cmsoft.fr.module.person.controller;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmsoft.fr.module.base.controller.Response;
import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.person.service.PersonService;

@RestController("/person")
public class PersonController {

	@Autowired()
	private PersonService personService;

	@PostMapping
	public ResponseEntity<Response<PersonDto>> save(@RequestBody PersonDto personDto) {

		Response<PersonDto> response = new Response<>();

		try {
			PersonDto savedDto = personService.save(personDto);
			response.setObject(savedDto);
			response.setHttpStatusCode(HttpServletResponse.SC_CREATED);
			response.setMessage("The person was saved successfull");
		} catch (IllegalArgumentException e) {
			response.setMessage(
					"Some mandatories person attributes are missing. Check api documetation for POST:/person");
			response.setHttpStatusCode(HttpServletResponse.SC_OK);
			response.setObject(personDto);
		} catch (EntityExistsException e) {
			response.setMessage(
					"The username is already registered. Try with other one");
			response.setHttpStatusCode(HttpServletResponse.SC_OK);
			response.setObject(personDto);
		}

		return new ResponseEntity<Response<PersonDto>>(response, response.getHttpStatus());
	}

	@Autowired
	public void setPersonSrvice(PersonService personService) {
		this.personService = personService;
	}

}
