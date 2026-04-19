package com.example.test;

import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig implements Resource {

    private DynamoDbClient dynamoDbClient;

    public DynamoDbConfig() {
        // Register for SnapStart hooks
        Core.getGlobalContext().register(this);
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
        return dynamoDbClient;
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    // Before snapshot — close connections
    @Override
    public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
        System.out.println("SnapStart: Closing DynamoDB connection before snapshot");
        if (dynamoDbClient != null) {
            dynamoDbClient.close();
        }
    }

    // After restore — reinitialize connections
    @Override
    public void afterRestore(Context<? extends Resource> context) throws Exception {
        System.out.println("SnapStart: Reinitializing DynamoDB connection after restore");
        dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}