package com.ramiro.films.newmove.usecase;

import com.ramiro.films.model.Match;
import com.ramiro.films.model.User;

import java.util.Optional;

public interface MatchRepo {

    Optional<Match> getMathOpenByUser(User user);

}