package com.ramiro.films.controller;

import com.ramiro.films.domain.Quiz;
import com.ramiro.films.dto.MoveResponseDto;
import com.ramiro.films.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizNewMoveController {

    private final Quiz quiz;

    @Operation(
            summary = "nova jogada",
            description = "inicia uma nova jogada",
            security = {
                    @SecurityRequirement(name = "BearerJWT")
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Jogada criada com sucesso.",
                        content = {
                                    @Content(
                                            schema = @Schema(implementation = MoveResponseDto.class),
                                            mediaType = "application/json")
                        })
    })
    @PostMapping("/newMove")
    public MoveResponseDto newMove(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        return MoveResponseDto.of(quiz.newMove(userDto.getUser()));

    }

}
