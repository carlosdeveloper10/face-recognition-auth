package com.cmsoft.fr.module.recognition.controller;

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
import com.cmsoft.fr.module.recognition.service.FaceRecognitionRequestDto;
import com.cmsoft.fr.module.recognition.service.RecognitionDto;
import com.cmsoft.fr.module.recognition.service.RecognitionService;

@RestController
@RequestMapping("/recognition/people")
public class RecognitionController {

	@Autowired
	private RecognitionService recognitionService;
	
	private final Logger logger = LoggerFactory.getLogger(RecognitionController.class);

	@PostMapping
	public ResponseEntity<Response<RecognitionDto>> faceRecognition(@RequestBody FaceRecognitionRequestDto requestDto) {
		
		Response<RecognitionDto> response = new Response<>();

		try {
			RecognitionDto recognitionDto = recognitionService.recognize(requestDto);
			response.setObject(recognitionDto);
			response.setHttpStatusCode(HttpServletResponse.SC_CREATED);
			response.setMessage(recognitionDto.getRecognition().toString());
		} catch (IllegalArgumentException e) {
			response.setMessage("Some request mandatories "
					+ "attributes are missing. Check api documetation for POST:/recognition/face");
			response.setHttpStatusCode(HttpServletResponse.SC_OK);
			logger.error("Error tryng to identify... error info: [{}]", e.getMessage());
		}catch (Exception e) {
			response.setMessage("An error has occurred, we are working to solve it.");
			response.setHttpStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error("Error tryng to identify... error info: [{}]", e.getMessage());
		}

		return new ResponseEntity<>(response, response.getHttpStatus());
	}
}
