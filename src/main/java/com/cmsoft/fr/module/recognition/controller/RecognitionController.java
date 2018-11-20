package com.cmsoft.fr.module.recognition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmsoft.fr.module.base.controller.Response;

/**
 * RestController that receive request to recognition a face and looking for a
 * matching face in database.
 * 
 * @author Carlos Mario
 *
 */
@RestController("/recognition")
public class RecognitionController {

	@GetMapping()
	private ResponseEntity lookForAMatch() {

		Response response = new Response<>();

		response.setHttpStatusCode(200);
		response.setMessage("Ok");
		response.setObject(new DetentionDto());

		return new ResponseEntity(response, response.getHttpStatus());
	}
}
