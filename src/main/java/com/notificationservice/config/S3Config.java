package com.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    public AWSCredentials credentials() {
	AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
	return credentials;
    }

    @Bean
    public AmazonS3 amazonS3() {
	AmazonS3 s3client = AmazonS3ClientBuilder.standard()
		.withCredentials(new AWSStaticCredentialsProvider(credentials()))
		.withEndpointConfiguration(getEndpointConfiguration(s3Url)).build();
	return s3client;
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(String url) {
	return new AwsClientBuilder.EndpointConfiguration(url, Regions.fromName(region).getName());
    }

}
