package com.ramiro.films.domain;

import com.ramiro.films.dto.MoveFeedbackResponseDto;
import com.ramiro.films.dto.MoveRequestDto;
import com.ramiro.films.model.Match;
import com.ramiro.films.model.Move;
import com.ramiro.films.model.User;

public interface Quiz {

    Match newMatch(User user);

    Move newMove(User user);

    MoveFeedbackResponseDto sendMove(User user, MoveRequestDto moveRequestDto);

}
