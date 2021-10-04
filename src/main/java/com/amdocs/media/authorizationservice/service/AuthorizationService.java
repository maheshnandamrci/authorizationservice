package com.amdocs.media.authorizationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.amdocs.media.authorizationservice.config.LoginFormVO;
import com.amdocs.media.authorizationservice.config.UserConstants;
import com.amdocs.media.authorizationservice.dao.AuthorizationDetails;
import com.amdocs.media.authorizationservice.dao.RegistrationForm;
import com.amdocs.media.authorizationservice.repository.AuthorizationDetailsRepository;
import com.amdocs.media.authorizationservice.repository.RegistrationServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AuthorizationService {

	Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private RegistrationServiceRepository registrationServiceRepo;

	@Autowired
	private AuthorizationDetailsRepository authorizationDetailsRepo;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.name}")
	private String topicName;

	public void sendMessage(String msg) {
		kafkaTemplate.send(topicName, msg);
	}

	/**
	 * This function used to insert the user details for the first time in
	 * database
	 */
	public Boolean registerUser(RegistrationForm registrationForm) {
		try {
			if (null != registrationForm) {
				registrationServiceRepo.save(registrationForm);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * This function is used to authenticate the user and insert the login
	 * details in DB
	 */
	public String isUserExistsInDatabase(LoginFormVO loginForm) {
		try {
			if (null != loginForm) {
				RegistrationForm registrationForm = registrationServiceRepo
						.findByUsername(loginForm.getUsername());
				if (null != registrationForm) {
					if (null != loginForm.getUsername()
							&& loginForm.getUsername().equalsIgnoreCase(
									registrationForm.getUsername())
							&& null != loginForm.getPassword()
							&& loginForm.getPassword().equalsIgnoreCase(
									registrationForm.getPassword())) {
						logger.info("username & password matched with DB");
						if (null == authorizationDetailsRepo
								.findByUsername(loginForm.getUsername())) {
							logger.info("username & password inserted in DB");
							authorizationDetailsRepo
									.save(new AuthorizationDetails(loginForm
											.getUsername(), loginForm
											.getPassword()));
						}
						/*
						 * invoke user profile operations if only user is
						 * authenticated
						 */
						return invokeUserProfileAPIs(loginForm);
					}
				} else {
					logger.info(UserConstants.USER_NOT_IN_DB);
				}
			} else {
				logger.info("loginForm is null, please check the input request");
			}
			return UserConstants.USER_NOT_AUTHORIZED;
		} catch (Exception ex) {
			ex.printStackTrace();
			return UserConstants.USER_NOT_AUTHORIZED;
		}
	}

	/*
	 * this method will invoke User Profile service API(s) and send message to
	 * kafka topic for Update/Delete operations
	 */
	private String invokeUserProfileAPIs(LoginFormVO loginFormVO)
			throws JsonProcessingException {

		String responseMessage = UserConstants.STRING_EMPTY;

		logger.info("Operation value is : " + loginFormVO.getOperation()
				+ " - URL is : " + UserConstants.USER_PROFILE_ENDPOINT_URL);
		if (null == loginFormVO.getOperation()) {
			responseMessage = UserConstants.USER_AUTHORIZED;
		} else if (loginFormVO.getOperation().equalsIgnoreCase(
				UserConstants.CREATE)) {
			/*
			 * invoking user profile create profile API using restTemplate
			 */
			responseMessage = restTemplate.postForObject(
					UserConstants.USER_PROFILE_ENDPOINT_URL, loginFormVO,
					String.class);

			logger.info("Posting " + loginFormVO.getOperation()
					+ "UserProfile message to Kafka topic");

			 sendMessage(mapper.writeValueAsString(loginFormVO));

			logger.info("Create user profile response - " + responseMessage);

		} else if (loginFormVO.getOperation().equalsIgnoreCase(
				UserConstants.UPDATE)) {

			responseMessage = webClientBuilder.build().put()
					.uri(UserConstants.USER_PROFILE_ENDPOINT_URL)
					.body(Mono.just(loginFormVO), LoginFormVO.class).retrieve()
					.bodyToMono(String.class).block();

			logger.info("Posting " + loginFormVO.getOperation()
					+ "UserProfile message to Kafka topic");

			 sendMessage(mapper.writeValueAsString(loginFormVO));

			logger.info("Update user profile response - " + responseMessage);

		} else if (loginFormVO.getOperation().equalsIgnoreCase(
				UserConstants.DELETE)) {

			webClientBuilder
					.build()
					.delete()
					.uri(UserConstants.USER_PROFILE_ENDPOINT_URL + "/"
							+ loginFormVO.getPhoneNumber()).retrieve()
					.bodyToMono(Void.class).block();

			logger.info("Posting " + loginFormVO.getOperation()
					+ "UserProfile message to Kafka topic");

			 sendMessage(mapper.writeValueAsString(loginFormVO));

			logger.info("Delete user profile response - " + responseMessage);
		}
		return responseMessage;
	}
}
