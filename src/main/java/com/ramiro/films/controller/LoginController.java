package com.ramiro.films.controller;

import com.ramiro.films.config.UserAuthenticationProvider;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.dto.ErrorDto;
import com.ramiro.films.dto.UserDto;
import com.ramiro.films.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class LoginController {

    private final UserAuthenticationProvider authenticationProvider;
    private final LoginService loginService;
    private final Quiz quiz;

    @Operation(summary = "login", description = "faz o login no card game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login efetuado com sucesso.",
                    content = {@Content(schema = @Schema(implementation = UserDto.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais inválidas.",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")})
    })
    @RequestBody(content = {@Content(schema = @Schema(implementation = CredentialsRequest.class))})
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Parameter(hidden = true) @AuthenticationPrincipal UserDto user) {
        user.setToken(authenticationProvider.createToken(user.getUsername()));
        loginService.save(user);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "logout", description = "Deslogar do aplicativo",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(@Parameter(hidden = true) @AuthenticationPrincipal UserDto user) {
        loginService.logout(user);
        quiz.finalizeMatch(user.getUser());
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(200).build();
    }

}
