package com.amdocs.media.authorizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.authorizationservice.dao.AuthorizationDetails;

@Repository
public interface AuthorizationDetailsRepository extends
		CrudRepository<AuthorizationDetails, Long> {

	AuthorizationDetails findByUsername(String username);

}
