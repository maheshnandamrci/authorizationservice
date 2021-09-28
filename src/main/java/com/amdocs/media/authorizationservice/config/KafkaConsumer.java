package com.amdocs.media.authorizationservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumer {

	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	RestTemplate restTemplate;

	@KafkaListener(topics = "maheshtesttopic", groupId = "maheshtestGrp")
	public void listenGroupFoo(String message) throws JsonMappingException,
			JsonProcessingException {
		logger.info("Received kafka message " + message);

		/*
		 * Reading the message from topic and converting it to request object
		 */

		ObjectMapper mapper = new ObjectMapper();
		LoginFormVO loginForm = mapper.readValue(message, LoginFormVO.class);

		/*
		 * based on the operation name respective user profile PUT/DELETE API
		 * will be invoked with required request object
		 */
		if (loginForm.getOperation().equalsIgnoreCase(UserConstants.UPDATE)) {
			UserProfileRequest updateUserRequest = new UserProfileRequest(
					loginForm.getUsername(), loginForm.getPhoneNumber());
			restTemplate.put(UserConstants.USER_PROFILE_ENDPOINT_URL, updateUserRequest);
			logger.info(UserConstants.USER_PROFILE_UPDATED);
		} else if (loginForm.getOperation().equalsIgnoreCase(
				UserConstants.DELETE)) {
			UserProfileRequest deleteUserRequest = new UserProfileRequest(
					loginForm.getUsername(), loginForm.getPhoneNumber());
			restTemplate.delete(UserConstants.USER_PROFILE_ENDPOINT_URL,
					deleteUserRequest);
			logger.info(UserConstants.USER_PROFILE_DELETED);
		}
	}

}
