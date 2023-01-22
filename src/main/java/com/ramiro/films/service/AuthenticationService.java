package com.ramiro.films.service;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;

public interface AuthenticationService {
	
	User validateCredentials(CredentialsRequest credentials);
	
	User findByUsername(String username);

	Login findLogin(User user);
}
