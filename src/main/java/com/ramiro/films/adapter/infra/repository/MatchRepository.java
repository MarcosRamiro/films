package com.ramiro.films.adapter.infra.repository;

import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.type.StatusMatchEnum;
import com.ramiro.films.domain.usecase.repository.AllMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends AllMatches, JpaRepository<Match, Long> {

    Optional<Match> findTop1ByUserAndStatus(User user, StatusMatchEnum status);

    @Override
    default Optional<Match> getMathOpenByUser(User user) {
        return this.findTop1ByUserAndStatus(user, StatusMatchEnum.OPEN);
    }

    @Override
    default void saveMatch(Match match) {
        this.save(match);
    }

    @Override
    default List<Match> getAll() {
        return this.findAll();
    }
}
