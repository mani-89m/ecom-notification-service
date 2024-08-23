package com.notificationservice.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.dynamodb.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class S3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	    false);

    @Value("${cloud.aws.s3.bucket-name}")
    private String s3BucketName;

    @Autowired
    private AmazonS3 amazonS3Client;

    public void putObject(final String message) {
	Order order = null;
	try {
	    order = mapper.readValue(message, Order.class);
	} catch (JsonProcessingException e) {
	    LOGGER.error("couldn't convert to Order object {}", e);
	}
	String fileName = order == null ? UUID.randomUUID().toString() : order.getOrderId();
	amazonS3Client.putObject(s3BucketName, fileName, message);
    }

    public void listObjects() {
	ObjectListing objectListing = amazonS3Client.listObjects(s3BucketName);
	LOGGER.info("S3 objects summary {}", objectListing.getObjectSummaries());
    }

}
