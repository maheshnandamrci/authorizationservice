package com.amdocs.media.authorizationservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.amdocs.media.authorizationservice.config.LoginFormVO;
import com.amdocs.media.authorizationservice.dao.AuthorizationDetails;
import com.amdocs.media.authorizationservice.repository.AuthorizationDetailsRepository;
import com.amdocs.media.authorizationservice.repository.RegistrationServiceRepository;
import com.amdocs.media.authorizationservice.service.AuthorizationService;

@RunWith(SpringRunner.class)
public class AuthorizationControllerTest {

	Logger logger = LoggerFactory.getLogger(AuthorizationControllerTest.class);

	@InjectMocks
	private AuthorizationController authorizationController;

	@Mock
	AuthorizationDetailsRepository authorizationDetailsRepo;

	@Mock
	RegistrationServiceRepository registrationServiceRepo;

	@Mock
	AuthorizationService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void authenticateUserTest() throws Exception {
		AuthorizationDetails authDeatils = new AuthorizationDetails("Mahesh",
				"Nandam");
		when(authorizationDetailsRepo.findByUsername("Mahesh")).thenReturn(
				authDeatils);
		logger.info(" ------------- dummy record inserted to DB -------------");
		LoginFormVO loginFormVO = new LoginFormVO("Mahesh", "Nandam");
		ResponseEntity<String> responseEntity = authorizationController
				.authenticateUser(loginFormVO);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void authenticateUserTest1() throws Exception {
		AuthorizationDetails authDeatils = new AuthorizationDetails("Mahesh",
				"Nandam");
		when(authorizationDetailsRepo.findByUsername("Mahesh1")).thenReturn(
				authDeatils);
		logger.info(" ------------- dummy record inserted to DB -------------");
		LoginFormVO loginFormVO = new LoginFormVO("Mahesh1", "Nandam1");
		ResponseEntity<String> responseEntity = authorizationController
				.authenticateUser(loginFormVO);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

}
