package com.cmsoft.fr.module.person.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityExistsException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.person.service.PersonService;
import com.cmsoft.fr.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}

	// ------ TDD for save()
	@Test
	public void whenPersonHaveSomeMandatoryAttributesisMissingThenApiReturn200AndMessage() throws Exception {

		PersonDto personDtoRequest = new PersonDto();
		when(personService.save(org.mockito.ArgumentMatchers.any(PersonDto.class)))
				.thenThrow(IllegalArgumentException.class);

		mockMvc.perform(post("/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(personDtoRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Some mandatories person "
						+ "attributes are missing. Check api documetation for POST:/person"));

	}

	@Test
	public void whenPersonExistsApiThenReturn200AndMessage() throws Exception {
		PersonDto personDtoRequest = new PersonDto();
		when(personService.save(org.mockito.ArgumentMatchers.any(PersonDto.class)))
				.thenThrow(EntityExistsException.class);

		mockMvc.perform(post("/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(personDtoRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("The username is already registered. Try with other one"));
	}

	@Test
	public void whenServerHasAExceptionThenReturn500AndMessage() throws Exception {
		PersonDto personDtoRequest = new PersonDto();
		Answer<PersonDto> ans = new Answer<PersonDto>() {
			@Override
			public PersonDto answer(InvocationOnMock invocation) throws Throwable {
				throw new Exception();
			}
		};
		when(personService.save(org.mockito.ArgumentMatchers.any(PersonDto.class))).then(ans);

		mockMvc.perform(post("/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(personDtoRequest))).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("An error has occurred, we are working to solve it."));
	}

	@Test()
	public void whenPersonDoesNotExistApiThenReturn201AndMessageAndSavedDto() throws Exception {
		PersonDto personDtoRequest = new PersonDto();
		personDtoRequest.setUsername("carlos123");
		personDtoRequest.setPhotoName("photoname.jpg");
		
		PersonDto personDtoReturned = new PersonDto();
		personDtoReturned.setUsername(personDtoRequest.getUsername());
		personDtoReturned.setPhotoName(personDtoRequest.getPhotoName());
		personDtoReturned.setId(1);

		// when(personService.save(org.mockito.ArgumentMatchers.eq(personDtoRequest))).thenReturn(personDtoReturned);
		when(personService.save(org.mockito.ArgumentMatchers.any())).thenReturn(personDtoReturned);

		mockMvc.perform(post("/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(personDtoRequest))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.object.photoName").value("photoname.jpg"))
				.andExpect(jsonPath("$.object.id").value(1))
				.andExpect(jsonPath("$.message").value("The person was saved successfull"));

	}

}
