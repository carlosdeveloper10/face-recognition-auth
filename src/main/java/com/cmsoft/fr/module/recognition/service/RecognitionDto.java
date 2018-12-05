package com.cmsoft.fr.module.recognition.service;

import com.cmsoft.fr.module.base.service.NoEntityDto;
import com.cmsoft.fr.module.person.service.PersonDto;

public class RecognitionDto implements NoEntityDto {

	public RecognitionDto() {
	}
	
	private PersonDto matchedPerson;
	
	private Float confidence;
	
	private Float minimumRequestedConfidence;
	
	private Float plusConfidence;
	
	private PersonMatchingStatus recognition;

	public PersonDto getMatchedPerson() {
		return matchedPerson;
	}

	public void setMatchedPerson(PersonDto matchedPerson) {
		this.matchedPerson = matchedPerson;
	}

	public Float getConfidence() {
		return confidence;
	}

	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}

	public Float getMinimumRequestedConfidence() {
		return minimumRequestedConfidence;
	}

	public void setMinimumRequestedConfidence(Float minimumRequestedConfidence) {
		this.minimumRequestedConfidence = minimumRequestedConfidence;
	}

	public Float getPlusConfidence() {
		return plusConfidence;
	}

	public void setPlusConfidence(Float plusConfidence) {
		this.plusConfidence = plusConfidence;
	}

	public PersonMatchingStatus getRecognition() {
		return recognition;
	}

	public void setRecognition(PersonMatchingStatus recognition) {
		this.recognition = recognition;
	}
}
