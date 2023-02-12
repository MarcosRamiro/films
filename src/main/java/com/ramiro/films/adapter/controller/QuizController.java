package com.ramiro.films.adapter.controller;

import com.ramiro.films.adapter.dto.MatchResponse;
import com.ramiro.films.adapter.dto.MoveFeedbackResponse;
import com.ramiro.films.adapter.dto.MoveRequest;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.domain.entity.dto.*;
import com.ramiro.films.domain.exceptions.MatchNotFoundException;
import com.ramiro.films.domain.exceptions.MoveNotFoundException;
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
                    content = {@Content(schema = @Schema(implementation = MatchResponse.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/newMatch")
    public MatchResponse newMatch(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        return new MatchResponse(quiz.newMatch(userDto).getId());
    }

    @Operation(summary = "envia opção da jogada", description = "envia a jogada para validar o resultado.",
            security = {@SecurityRequirement(name = "BearerJWT")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Feedback da jogada.",
                    content = {@Content(schema = @Schema(implementation = MoveFeedbackResponse.class),
                            mediaType = "application/json")})
    })
    @PostMapping("/sendMove")
    public MoveFeedbackResponse sendMove(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @RequestBody MoveRequest moveRequest) throws MoveNotFoundException, MatchNotFoundException {
        return quiz.sendMove(userDto, moveRequest);
    }

}
