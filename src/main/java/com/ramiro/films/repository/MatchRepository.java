package com.ramiro.films.repository;

import com.ramiro.films.model.Match;
import com.ramiro.films.model.User;
import com.ramiro.films.type.StatusMatchEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findTop1ByUserAndStatus(User user, StatusMatchEnum status);

}
