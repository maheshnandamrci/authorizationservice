package com.amdocs.media.authorizationservice.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

import com.amdocs.media.authorizationservice.config.LoginFormVO;
import com.amdocs.media.authorizationservice.dao.AuthorizationDetails;
import com.amdocs.media.authorizationservice.dao.RegistrationForm;
import com.amdocs.media.authorizationservice.repository.AuthorizationDetailsRepository;
import com.amdocs.media.authorizationservice.repository.RegistrationServiceRepository;

public class AuthorizationServiceTest {

	@InjectMocks
	AuthorizationService service;

	@Mock
	RegistrationServiceRepository registrationServiceRepo;

	@Mock
	AuthorizationDetailsRepository authorizationDetailsRepo;

	@Mock
	AuthorizationService authorizationService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void isUserExistsInDatabaseTest() {

		when(registrationServiceRepo.findByUsername("Mahesh")).thenReturn(
				new RegistrationForm(1L, "Mahesh", "Nandam"));

		when(authorizationDetailsRepo.findByUsername("Mahesh")).thenReturn(
				new AuthorizationDetails("Mahesh", "Nandam"));

		LoginFormVO loginForm = new LoginFormVO("Mahesh", "Nandam");
		String response = service.isUserExistsInDatabase(loginForm);
		assertEquals("User is authorized", response);
	}

	@Test
	public void isUserExistsInDatabaseTest1() {

		when(authorizationDetailsRepo.findByUsername("Mahesh")).thenReturn(
				new AuthorizationDetails("Mahesh", "Nandam"));

		LoginFormVO loginForm = new LoginFormVO("Mahesh1", "Nandam1");
		String response = service.isUserExistsInDatabase(loginForm);
		assertEquals("User is not authorized", response);
	}

}
