package com.ramiro.films.domain.usecase;

import com.ramiro.films.domain.entity.dto.MoveDto;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Move;
import com.ramiro.films.domain.exceptions.MatchNotFoundException;

public interface NewMoveUseCase {

    MoveDto newMove(UserDto user) throws MatchNotFoundException;
}
