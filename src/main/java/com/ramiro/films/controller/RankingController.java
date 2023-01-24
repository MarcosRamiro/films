package com.ramiro.films.controller;

import com.ramiro.films.dto.RankingResponseDto;
import com.ramiro.films.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public RankingResponseDto ranking() {

        return rankingService.getRanking();

    }


}
