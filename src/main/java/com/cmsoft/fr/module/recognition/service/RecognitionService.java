package com.cmsoft.fr.module.recognition.service;

import com.cmsoft.fr.module.person.service.PersonDto;

/**
 * Offers a group of functions for AWS rekognition.
 * 
 * @author Carlos Mario
 *
 */
public interface RecognitionService {

	RecognitionDto recognize(FaceRecognitionRequestDto dto);
}
