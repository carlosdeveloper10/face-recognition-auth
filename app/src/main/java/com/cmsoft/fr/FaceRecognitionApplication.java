package com.cmsoft.fr;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDynamoDBRepositories
public class FaceRecognitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceRecognitionApplication.class, args);
	}
}
