package com.ramiro.films.domain.usecase.repository;

import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.User;

import java.util.List;
import java.util.Optional;

public interface AllMatches {

    Optional<Match> getMathOpenByUser(User user);

    void saveMatch(Match match);

    List<Match> getAll();

}