package com.ramiro.films.controller;

import com.ramiro.films.domain.Quiz;
import com.ramiro.films.dto.*;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final Quiz quiz;

    @Operation(summary = "nova partida", description = "inicia uma nova partida",
            security = {@SecurityRequirement(name = "BearerJWT")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Jogo criado com sucesso.",
                    content = {@Content(schema = @Schema(implementation = MatchResponseDto.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/newMatch")
    public MatchResponseDto newMatch(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        return new MatchResponseDto(quiz.newMatch(userDto.getUser()).getId());
    }


    @Operation(summary = "nova jogada", description = "inicia uma nova jogada",
            security = {@SecurityRequirement(name = "BearerJWT")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Jogada criada com sucesso.",
                    content = {@Content(schema = @Schema(implementation = MoveResponseDto.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/newMove")
    public MoveResponseDto newMove(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        return MoveResponseDto.of(quiz.newMove(userDto.getUser()));

    }


    @Operation(summary = "envia opção da jogada", description = "envia a jogada para validar o resultado.",
            security = {@SecurityRequirement(name = "BearerJWT")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Feedback da jogada.",
                    content = {@Content(schema = @Schema(implementation = MoveFeedbackResponseDto.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/sendMove")
    public MoveFeedbackResponseDto sendMove(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @RequestBody MoveRequestDto moveRequest) {
        return quiz.sendMove(userDto.getUser(), moveRequest);
    }

}
