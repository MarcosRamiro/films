package com.ramiro.films.service;

import java.util.List;

import com.ramiro.films.dto.Film;

public interface FilmService {
	
	List<Film> searchFilmByTitle(String id);

	Film getFilmById(String id);

}
