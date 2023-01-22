package com.ramiro.films.service.impl;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.repository.UserRepository;
import com.ramiro.films.service.AuthenticationService;
import com.ramiro.films.type.StatusLoginEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final LoginRepository loginRepository;

	@Override
	public User validateCredentials(CredentialsRequest credentials) {
		log.debug("Start Login validate credentials: " + credentials);
		User user = this.findByUsername(credentials.getUsername());

		if (isUserValid(user, credentials)) {
			log.info("Login efetuado com sucesso. username: " + user.getUsername());
			return user;
		}
		
		log.info("Invalided credentials");
		throw new AuthenticationCredentialsNotFoundException("invalided username/password");
	}

	private boolean isUserValid(User user, CredentialsRequest credentials) {
		return user.getPassword().equals(credentials.getPassword());
	}

	@Override
	public User findByUsername(String username) {

		Optional<User> userOptional = userRepository.findTop1ByUsername(username);

		if (userOptional.isPresent())
			return userOptional.get();

		log.info("user not found");
		throw new AuthenticationCredentialsNotFoundException("invalide username");

	}

	@Override
	public Login findLogin(User user) {

		Optional<Login> loginOptional = loginRepository.findTop1ByUserAndStatus(user, StatusLoginEnum.OPEN);
		if (loginOptional.isPresent())
			return loginOptional.get();

		log.info("login not found");

		throw new AuthenticationCredentialsNotFoundException("login not found");
	}

}
