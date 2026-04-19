package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class StreamLambdaHandler implements RequestStreamHandler, Resource {

	private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	static {
		try {
			handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(LambdaSnapstartApiGatewayApplication.class);
		} catch (ContainerInitializationException e) {
			throw new RuntimeException("Could not initialize Spring Boot application", e);
		}
	}

	public StreamLambdaHandler() {
		Core.getGlobalContext().register(this);
	}

	@Override
	public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
		System.out.println("SnapStart: beforeCheckpoint - preparing snapshot");
	}

	@Override
	public void afterRestore(Context<? extends Resource> context) throws Exception {
		System.out.println("SnapStart: afterRestore - reinitializing connections");
	}

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream,
			com.amazonaws.services.lambda.runtime.Context context) throws IOException {
		handler.proxyStream(inputStream, outputStream, context);
	}

}
