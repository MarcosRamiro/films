package com.ramiro.films.controller;

import com.ramiro.films.config.UserAuthenticationProvider;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.domain.impl.QuizImpl;
import com.ramiro.films.dto.UserDto;
import com.ramiro.films.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserAuthenticationProvider authenticationProvider;
    private final LoginService loginService;
    private final Quiz quiz;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@AuthenticationPrincipal UserDto user) {
        user.setToken(authenticationProvider.createToken(user.getUsername()));
        loginService.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user) {
        loginService.logout(user);
        quiz.finalizeMatch(user.getUser());
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

}
