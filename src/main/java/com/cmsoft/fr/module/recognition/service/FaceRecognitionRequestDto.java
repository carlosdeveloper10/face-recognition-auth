package com.cmsoft.fr.module.recognition.service;

import com.cmsoft.fr.module.base.service.NoEntityDto;

public class FaceRecognitionRequestDto implements NoEntityDto {

	private String base64Image;
	
	private Float minimumConfidence;

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public Float getMinimumConfidence() {
		return minimumConfidence;
	}

	public void setMinimumConfidence(Float minimumConfidence) {
		this.minimumConfidence = minimumConfidence;
	}
}
