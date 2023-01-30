package com.ramiro.films.repository;

import com.ramiro.films.model.Move;
import com.ramiro.films.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Long> {

    @Query("SELECT mo FROM Move mo, Match ma, User us, Film fia, Film fib where mo.match = ma and ma.user = us and mo.filmA = fia and mo.filmB = fib and us.id = ?1")
    List<Move> findAllMovesFromUserId(Long userId);

}
