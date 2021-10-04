package com.amdocs.media.authorizationservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConstants {

	public static final String STRING_EMPTY = "";
	public static final String USER_PROFILE_CREATED = "User Profile created";
	public static final String USER_PROFILE_UPDATED = "User Profile updated";
	public static final String USER_PROFILE_DELETED = "User Profile deleted";
	public static final String USER_AUTHORIZED = "User is authorized";
	public static final String USER_NOT_AUTHORIZED = "User is not authorized";
	public static final String USER_NOT_IN_DB = "User not found in DB";
	public static String USER_PROFILE_ENDPOINT_URL = "http://localhost:9080/assignement/profile";

	public static final String CREATE = "Create";
	public static final String UPDATE = "Update";
	public static final String DELETE = "Delete";

}
