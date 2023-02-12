package com.ramiro.films.domain;

import com.ramiro.films.adapter.dto.MoveFeedbackResponse;
import com.ramiro.films.adapter.dto.MoveRequest;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.exceptions.MatchNotFoundException;
import com.ramiro.films.domain.exceptions.MoveNotFoundException;

public interface Quiz {

    Match newMatch(UserDto userDto);

    MoveFeedbackResponse sendMove(UserDto userDto, MoveRequest moveRequest) throws MoveNotFoundException, MatchNotFoundException;

}
