package com.ramiro.films.newmove.adapter.repo;

import com.ramiro.films.model.Match;
import com.ramiro.films.model.User;
import com.ramiro.films.newmove.usecase.MatchRepo;
import com.ramiro.films.type.StatusMatchEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends MatchRepo, JpaRepository<Match, Long> {

    Optional<Match> findTop1ByUserAndStatus(User user, StatusMatchEnum status);

    @Override
    default Optional<Match> getMathOpenByUser(User user) {
        return this.findTop1ByUserAndStatus(user,StatusMatchEnum.OPEN);
    }
}
