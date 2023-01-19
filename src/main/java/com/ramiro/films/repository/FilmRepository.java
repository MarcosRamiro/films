package com.ramiro.films.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.dao.Film;

public interface FilmRepository extends  JpaRepository<Film, Long>  {
	
	List<Film> findByImdbId(String imdbId);
	
	List<Film> findTop2ByOrderByTitleAsc();

}
