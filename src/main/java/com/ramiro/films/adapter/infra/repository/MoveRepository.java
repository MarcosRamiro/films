package com.ramiro.films.adapter.infra.repository;

import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.entity.model.Move;
import com.ramiro.films.domain.usecase.repository.AllMoves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends AllMoves, JpaRepository<Move, Long> {

    @Query("SELECT mo FROM Move mo, Match ma, User us, Film fia, Film fib where mo.match = ma and ma.user = us and mo.filmA = fia and mo.filmB = fib and us.id = ?1")
    List<Move> findAllMovesFromUserId(Long userId);

    @Override
    default List<Move> getAllMovesFromUser(User user) {
        return this.findAllMovesFromUserId(user.getId());
    }

    @Override
    default void add(Move move){
        this.save(move);
    }
}
