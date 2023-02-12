package com.ramiro.films.domain.usecase.repository;

import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.entity.model.Move;

import java.util.List;

public interface AllMoves {

    List<Move> getAllMovesFromUser(User user);

    void add(Move move);
}
