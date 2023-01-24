package com.ramiro.films.domain;

import java.util.List;

import com.ramiro.films.model.Film;

public interface FilmDataBase {

	List<Film> getAllFilms();

	List<Film> getTwoFilms();

	void uploadFilms();

}
