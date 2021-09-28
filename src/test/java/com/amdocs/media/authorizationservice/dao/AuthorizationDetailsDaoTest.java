package com.amdocs.media.authorizationservice.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amdocs.media.authorizationservice.repository.AuthorizationDetailsRepository;
import com.amdocs.media.authorizationservice.repository.RegistrationServiceRepository;

public class AuthorizationDetailsDaoTest {

	@Mock
	RegistrationServiceRepository registrationRepo;

	@Mock
	AuthorizationDetailsRepository authorizationRepo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindByUserName() {

		when(authorizationRepo.findByUsername("Mahesh")).thenReturn(
				new AuthorizationDetails("Mahesh", "Nandam"));

		AuthorizationDetails authDetails = authorizationRepo
				.findByUsername("Mahesh");

		assertEquals("Mahesh", authDetails.getUsername());
		assertEquals("Nandam", authDetails.getPassword());
	}

	@Test
	public void testFindByUserName1() {

		AuthorizationDetails authDetails = authorizationRepo
				.findByUsername("Mahesh");
		assertEquals(null, authDetails);
	}

	@Test
	public void testFindByUserName2() {

		when(authorizationRepo.findByUsername("Mahesh")).thenReturn(
				new AuthorizationDetails("Mahesh1", "Nandam1"));

		AuthorizationDetails authDetails = authorizationRepo
				.findByUsername("Mahesh");

		assertNotEquals("Mahesh", authDetails.getUsername());
		assertNotEquals("Nandam", authDetails.getPassword());
	}

}
