package com.ramiro.films.adapter.controller;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.usecase.FinalizeMatch;
import com.ramiro.films.domain.usecase.LogoutUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class LogoutController {

    private final LogoutUseCase logout;
    private final FinalizeMatch finalizeMatch;

    @Operation(summary = "logout", description = "Deslogar do aplicativo",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(@Parameter(hidden = true) @AuthenticationPrincipal UserDto user) {
        logout.signOut(user);
        finalizeMatch.finalizeIfPresent(user);
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(200).build();
    }
}
