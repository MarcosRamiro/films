package com.ramiro.films.adapter.controller;

import com.ramiro.films.adapter.dto.RankingResponse;
import com.ramiro.films.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @Operation(summary = "ranking", description = "obtem o ranking dos jogadores.",
            security = {@SecurityRequirement(name = "BearerJWT")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ranking dos jogadores.",
                    content = {@Content(schema = @Schema(implementation = RankingResponse.class),
                            mediaType = "application/json")})
    })
    @GetMapping
    public RankingResponse ranking() {

        return rankingService.getRanking();

    }

}
