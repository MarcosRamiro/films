package com.ramiro.films.controller;

import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.config.UserAuthenticationProvider;
import com.ramiro.films.dto.UserDto;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class LoginController {

	private final UserAuthenticationProvider authenticationProvider;
	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@AuthenticationPrincipal UserDto user) {
		user.setToken(authenticationProvider.createToken(user.getUsername()));
		loginService.save(user);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user) {
		loginService.logout(user);
		SecurityContextHolder.clearContext();
		return ResponseEntity.noContent().build();
	}

}
