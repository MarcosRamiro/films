package com.ramiro.films.domain;

import java.util.List;

import com.ramiro.films.dao.Film;

public interface FilmDataBase {

	List<Film> getAllFilms();

	List<Film> getTwoFilms();

}
