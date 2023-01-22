package com.ramiro.films.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.model.Move;

public interface MoveRepository extends JpaRepository<Move, Long>  {

}
