package com.ramiro.films.adapter.controller;

import com.ramiro.films.adapter.config.UserAuthenticationProvider;
import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.adapter.dto.ErrorDto;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.usecase.CreateLoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserAuthenticationProvider authenticationProvider;
    private final CreateLoginUseCase createLoginUseCase;

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
    public ResponseEntity<UserDto> login(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        userDto.setToken(authenticationProvider.createToken(userDto.getUsername()));
        createLoginUseCase.save(userDto);
        return ResponseEntity.ok(userDto);
    }

}
