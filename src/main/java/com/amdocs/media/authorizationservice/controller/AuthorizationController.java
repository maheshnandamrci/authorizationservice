package com.amdocs.media.authorizationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.authorizationservice.config.LoginFormVO;
import com.amdocs.media.authorizationservice.dao.RegistrationForm;
import com.amdocs.media.authorizationservice.service.AuthorizationService;

@RestController
@RequestMapping(value = "assignement")
public class AuthorizationController {

	Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private AuthorizationService authorizationService;

	@PostMapping("/registerUser")
	public ResponseEntity<Boolean> registerUser(
			@RequestBody RegistrationForm registrationForm) {
		logger.info("Request received for registering the user");
		return new ResponseEntity<Boolean>(
				authorizationService.registerUser(registrationForm),
				HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(
			@RequestBody LoginFormVO loginFormVO) {
		logger.info("Request received for login");
		String responseMessage = authorizationService
				.isUserExistsInDatabase(loginFormVO);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);

	}

}
