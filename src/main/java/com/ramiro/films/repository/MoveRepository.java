package com.ramiro.films.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.dao.Move;

public interface MoveRepository extends JpaRepository<Move, Long>  {

}
