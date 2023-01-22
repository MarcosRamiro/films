package com.ramiro.films.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.model.Match;

public interface MatchRepository  extends JpaRepository<Match, Long>  {

}
