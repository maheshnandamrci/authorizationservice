package com.amdocs.media.authorizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.authorizationservice.dao.RegistrationForm;

@Repository
public interface RegistrationServiceRepository extends
		CrudRepository<RegistrationForm, Long> {

	RegistrationForm findByUsername(String username);

}
