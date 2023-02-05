package com.ramiro.films.newmove.usecase;

import com.ramiro.films.model.User;
import com.ramiro.films.newmove.entity.Move;

import java.util.List;

public interface AllMoves {

    List<Move> getAllMovesFromUser(User user);
}
