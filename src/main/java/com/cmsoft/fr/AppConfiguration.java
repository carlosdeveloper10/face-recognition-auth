package com.cmsoft.fr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

@Configuration
public class AppConfiguration {

	@Bean
	public AmazonRekognition getAwsRekognition() {
		return AmazonRekognitionClientBuilder.defaultClient();
	}
	
	
}
