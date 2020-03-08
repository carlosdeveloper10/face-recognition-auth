package com.cmsoft.fr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfiguration {

	@Bean
	public AmazonRekognition getAwsRekognition() {
		return AmazonRekognitionClientBuilder.defaultClient();
	}

	@Bean(name = "amazonDynamoDB")
	public AmazonDynamoDB getAwsDynamoDb() {
		return AmazonDynamoDBClientBuilder.defaultClient();
	}
	
	@Bean(name = "amazonS3")
	public AmazonS3 getAwsS3() {
		return AmazonS3ClientBuilder.defaultClient();
	}
}
