package com.cmsoft.fr.module.recognition.controller;

import java.io.Serializable;

public class DetentionDto implements Serializable{

	private double confidence;

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	
}
