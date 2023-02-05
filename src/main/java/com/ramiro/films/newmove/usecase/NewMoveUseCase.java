package com.ramiro.films.newmove.usecase;

import com.ramiro.films.model.User;
import com.ramiro.films.newmove.entity.Move;

public interface NewMoveUseCase {

    Move newMove(User user);
}
