package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.model.User;
import lombok.Data;

@Data
public class UserDto {

	@JsonProperty("username")
	private String username;

	@JsonProperty("token")
	private String token;

	public UserDto(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public static UserDto of(User user) {
		return new UserDto(user.getUsername());
	}

}
