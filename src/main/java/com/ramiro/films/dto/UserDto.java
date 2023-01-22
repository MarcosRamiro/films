package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.model.User;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public class UserDto {

	@JsonProperty("username")
	private String username;

	@JsonProperty("token")
	private String token;

	@JsonIgnore
	private User user;

	private UserDto(){}
	private UserDto(String username, User user) {
		this.username = username;
		this.user = user;
	}

	public static UserDto of(User user) {
		Assert.notNull(user, "user is null");
		return new UserDto(user.getUsername(), user);
	}

}
