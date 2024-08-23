package com.notificationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class NotificationServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceController.class);

    @PostMapping("/notification")
    @ApiOperation(value = "Send notifications to the users along with order details", response = String.class)
    public String sendMessage() {
	// Since the requirement is not clear am keeping this part as unimplemented
	LOGGER.info("sending notifications to the users via email/sns");
	return "SUCCESS";
    }

}
