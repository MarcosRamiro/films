package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(String username, String password) {

	@JsonCreator
	public UserRequest(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}
	
}