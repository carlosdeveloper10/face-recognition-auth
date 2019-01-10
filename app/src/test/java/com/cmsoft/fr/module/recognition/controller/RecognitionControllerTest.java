package com.cmsoft.fr.module.recognition.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cmsoft.fr.module.person.service.PersonDto;
import com.cmsoft.fr.module.person.service.PersonService;
import com.cmsoft.fr.module.recognition.service.FaceRecognitionRequestDto;
import com.cmsoft.fr.module.recognition.service.PersonMatchingStatus;
import com.cmsoft.fr.module.recognition.service.RecognitionDto;
import com.cmsoft.fr.module.recognition.service.RecognitionService;
import com.cmsoft.fr.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class RecognitionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RecognitionService recognitionService;

	@InjectMocks
	private RecognitionController recognitionController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(recognitionController).build();
	}

	// ------ TDD for faceRecognition()
	@Test
	public void whenSomeMandatoryAttributesIsMissingThenApiReturn200AndMessage() throws Exception {

		FaceRecognitionRequestDto requestDto = new FaceRecognitionRequestDto();
		when(recognitionService.recognize(Mockito.any(FaceRecognitionRequestDto.class)))
				.thenThrow(IllegalArgumentException.class);

		mockMvc.perform(post("/recognition/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(requestDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Some request mandatories "
						+ "attributes are missing. Check api documetation for POST:/recognition/face"));

	}

	@Test
	public void whenServerHasAExceptionThenReturn500AndMessage() throws Exception {
		FaceRecognitionRequestDto requestDto = new FaceRecognitionRequestDto();

		Answer<PersonDto> ans = new Answer<PersonDto>() {
			@Override
			public PersonDto answer(InvocationOnMock invocation) throws Throwable {
				throw new Exception();
			}
		};
		when(recognitionService.recognize(Mockito.any(FaceRecognitionRequestDto.class))).then(ans);

		mockMvc.perform(post("/recognition/people").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(requestDto))).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("An error has occurred, we are working to solve it."));
	}

	@Test
	public void whenAllIsOkApiThenReturn201AndMessage() throws Exception {
		PersonDto personDto = new PersonDto();
		personDto.setUsername("carlos123");
		personDto.setPhotoName("photoname.jpg");
		personDto.setBase64Photo("base_x_64_photo");

		FaceRecognitionRequestDto requestDto = new FaceRecognitionRequestDto();

		RecognitionDto recognitionDto = new RecognitionDto();
		recognitionDto.setMatchedPerson(personDto);
		recognitionDto.setConfidence(99.5f);
		recognitionDto.setMinimumRequestedConfidence(99f);
		recognitionDto.setRecognition(PersonMatchingStatus.FACIAL_FEATURES_FOUND);
		recognitionDto.setPlusConfidence(0.5f);

		when(recognitionService.recognize(Mockito.any(FaceRecognitionRequestDto.class))).thenReturn(recognitionDto);

		mockMvc.perform(
				post("/recognition/people").contentType(MediaType.APPLICATION_JSON).content(TestUtil.asJsonString(requestDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("FACIAL_FEATURES_FOUND"))
				.andExpect(jsonPath("$.object.matchedPerson.username").value("carlos123"))
				.andExpect(jsonPath("$.object.matchedPerson.photoName").value("photoname.jpg"))
				.andExpect(jsonPath("$.object.matchedPerson.base64Photo").value("base_x_64_photo"))
				.andExpect(jsonPath("$.object.confidence").value(99.5f))
				.andExpect(jsonPath("$.object.minimumRequestedConfidence").value(99f))
				.andExpect(jsonPath("$.object.plusConfidence").value(0.5f));
	}
}
