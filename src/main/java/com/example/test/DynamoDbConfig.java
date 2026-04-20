package com.example.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

	private DynamoDbClient dynamoDbClient;

	@Bean
	public DynamoDbClient dynamoDbClient() {
		dynamoDbClient = DynamoDbClient.builder().region(Region.US_EAST_1).build();
		return dynamoDbClient;
	}

	@Bean
	public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
		return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
	}

}