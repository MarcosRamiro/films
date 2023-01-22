package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsRequest {

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

}