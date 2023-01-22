package com.ramiro.films.controller;

import com.ramiro.films.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
public class Hello {
	
	@GetMapping
	public ResponseEntity<String> hello(@AuthenticationPrincipal UserDto user) {
		log.info("Hello World!!");
		return ResponseEntity.ok("Hello, " + user.getUsername());
	}

}
