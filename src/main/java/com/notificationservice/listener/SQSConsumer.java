package com.notificationservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import com.notificationservice.service.S3Service;

@Component
public class SQSConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSConsumer.class);
    
    @Autowired
    private S3Service s3Service;

    @SqsListener("${cloud.aws.queue.name}")
    public void receiveMessage(String message) {
	LOGGER.info("SQS Message Received : {}", message);
	s3Service.putObject(message);
	LOGGER.info("S3 Upload Completed for order : {}", message);
	LOGGER.info("Notifictaion has been sent for order : {}", message);
    }
}
