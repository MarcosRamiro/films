package com.ramiro.films.controller;

import com.ramiro.films.domain.Quiz;
import com.ramiro.films.dto.*;
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

    @PostMapping("/newMatch")
    public MatchResponseDto newMatch(@AuthenticationPrincipal UserDto userDto) {
        return new MatchResponseDto(quiz.newMatch(userDto.getUser()).getId());
    }

    @PostMapping("/newMove")
    public MoveResponseDto newMove(@AuthenticationPrincipal UserDto userDto) {
        return MoveResponseDto.of(quiz.newMove(userDto.getUser()));

    }

    @PostMapping("/sendMove")
    public MoveFeedbackResponseDto sendMove(@AuthenticationPrincipal UserDto userDto, @RequestBody MoveRequestDto moveRequest) {
        return quiz.sendMove(userDto.getUser(), moveRequest);
    }

}
