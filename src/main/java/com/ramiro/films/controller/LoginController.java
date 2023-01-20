package com.ramiro.films.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.dao.Login;
import com.ramiro.films.dao.User;
import com.ramiro.films.dto.UserRequest;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final UserRepository userRepository;
	private final LoginRepository loginRepository;

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	public LoginController(UserRepository userRepository,
							LoginRepository loginRepository) {
		this.userRepository = userRepository;
		this.loginRepository = loginRepository;
	}

	@PostMapping
	public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {

		Optional<User> userOptional = userRepository.findTop1ByUsername(userRequest.username());

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			if (isUserValid(user)) {
				
				Optional<Login> loginOptional = loginRepository.findTop1ByUserAndDateTimeEndIsNull(user);
				
				if (!loginOptional.isPresent()) {
					loginRepository.save(new Login(user));
					logger.info("Login efetuado com sucesso. username: " + user.getUsername());
				} else {
					logger.info("Usuário já logado no Sistema. username: " + user.getUsername());
				}
				
				return ResponseEntity.ok("Sucesso..");
			}
		}

		return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("errroooouuuu");

	}

	private boolean isUserValid(User user) {

		if (user.getPassword().equals(user.getPassword())) {
			return true;
		}
		return false;

	}

}
